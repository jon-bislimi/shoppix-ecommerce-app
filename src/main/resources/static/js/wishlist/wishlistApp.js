class WishlistApp {
    constructor() {
        this.wishlistApi = new WishlistApi();
        this.productApi = new ProductApi();
        this.shoppingCartApi = new ShoppingCartApi();
        this.userId = this.getUserId();
        this.products = [];
        this.productIds = [];
        this.container = document.getElementById('container')
    }

    async initialize() {
        await this.initWishlist();
    }

    async initWishlist() {
        try {
            this.wishlist = await this.wishlistApi.getWishlistByUserId(this.userId);

            // Create new wishlist if none exists
            if (!this.wishlist) {
                this.wishlist = await this.wishlistApi.createWishlist(this.userId);
                this.wishlist.productIds = []; // Initialize productIds
            }

            // Ensure productIds is always an array
            console.log(`productIds: ${this.wishlist.productIds}`);
            this.productIds = this.wishlist.productIds || [];
            await this.init();
        } catch (error) {
            console.error("Failed to initialize wishlist:", error);
        }
    }

    async init() {
        try {
            for (const id of this.productIds) {
                const product = await this.productApi.findById(id);
                console.log(product);
                this.products.push(product);
            }

            console.log('Products in wishlist:', this.products);
            this.renderProducts(this.products);
        } catch (error) {
            console.error("Failed to load products:", error);
        }
    }

    async addProductToWishlist(productId) {
        if (this.userId === 'guest') {
            window.location.href = "http://localhost:8080/auth/login";
            alert("Please login first");
            return;
        }
        const response = await this.wishlistApi.addProductToWishlist(this.userId, productId);
        if (response.ok) {
            const productResponse = await this.productApi.findById(productId);
            if (productResponse.ok) {
                const product = await productResponse.json();
                this.productIds.push(productId);
                this.products.push(product);
                this.renderProducts(this.products);
            }
        }
    }

    async removeProductFromWishlist(productId) {
        const response = await this.wishlistApi.removeProductFromWishlist(this.userId, productId);
        console.log(response);
        if (response.ok) {
            const index = this.productIds.indexOf(productId);
            console.log(index);
            console.log(this.productIds);
            this.productIds.splice(index, 1);
            this.products.splice(index, 1);
            this.renderProducts(this.products);
            window.location.reload();
        }
    }

    async clearWishlist() {
        const response = await this.wishlistApi.clearWishlist(this.userId);
        if (response.ok) {
            this.productIds = [];
            this.products = [];
            this.renderProducts(this.products);
        }
    }

    async isProductInWishlist(productId) {
        return await this.wishlistApi.isProductInWishlist(this.userId, productId) && this.productIds.includes(productId);
    }

    renderProducts(products) {
        if (!this.container) {
            alert("Container not found");
            return;
        }
        this.container.innerHTML = '';
        if (products.length === 0) {
            const h2 = document.createElement('h2');
            h2.className = 'no-products-message';
            h2.textContent = 'No products in wishlist!';
            this.container.appendChild(h2);
            return;
        }

        products.forEach(product => {
            const rowDiv = document.createElement('div');
            rowDiv.className = 'row justify-content-center';
            rowDiv.style.marginTop = '20px'; // Add margin-top style

            const colDiv = document.createElement('div');
            colDiv.className = 'col-md-6';

            const cardDiv = document.createElement('div');
            cardDiv.className = 'card p-3';

            const innerRowDiv = document.createElement('div');
            innerRowDiv.className = 'row g-0';

            const imgColDiv = document.createElement('div');
            imgColDiv.className = 'col-md-6 d-flex align-items-center';

            const img = document.createElement('img');
            if (product.images && product.images.length > 0) {
                img.src = product.images[0];
            } else {
                img.src = 'https://dummyimage.com/450x300/dee2e6/6c757d.jpg';
            }
            img.className = 'product-image img-fluid';
            img.alt = 'Product Image';

            imgColDiv.appendChild(img);

            const bodyColDiv = document.createElement('div');
            bodyColDiv.className = 'col-md-6';

            const cardBodyDiv = document.createElement('div');
            cardBodyDiv.className = 'card-body';

            const h5 = document.createElement('h5');
            h5.className = 'card-title';
            h5.textContent = product.name;

            const descriptionP = document.createElement('p');
            descriptionP.className = 'card-text text-muted';
            descriptionP.textContent = product.description;

            const priceP = document.createElement('p');
            priceP.className = 'card-text';
            priceP.innerHTML = `<strong>Price: $${product.price}</strong>`;

            const addToCartBtn = document.createElement('button');
            addToCartBtn.className = 'btn btn-primary btn-custom mb-2';
            addToCartBtn.textContent = 'Add to Cart';
            addToCartBtn.addEventListener('click', (e) => {
                e.preventDefault();
                if (isNaN(this.userId)) {
                    this.userId = this.getUserId();
                    if (isNaN(this.userId)) {
                        window.location.href = "/auth/login";
                        alert("Please login first");
                        return;
                    }
                }
                if (this.userId === undefined || this.userId === 'guest') {
                    window.location.href = "/auth/login";
                    alert("Please login first");
                    return;
                }
                this.shoppingCartApi.addProductToShoppingCart(this.userId, product.id);
                alert("Product added to cart!");
            });

            const removeFromWishlistBtn = document.createElement('button');
            removeFromWishlistBtn.className = 'btn btn-danger btn-custom';
            removeFromWishlistBtn.textContent = 'Remove from Wishlist';
            removeFromWishlistBtn.onclick = async (event) => {
                event.preventDefault();
                event.stopPropagation();
                await this.removeProductFromWishlist(product.id);
                window.location.reload();
            }

            cardBodyDiv.appendChild(h5);
            cardBodyDiv.appendChild(descriptionP);
            cardBodyDiv.appendChild(priceP);
            cardBodyDiv.appendChild(addToCartBtn);
            cardBodyDiv.appendChild(removeFromWishlistBtn);

            bodyColDiv.appendChild(cardBodyDiv);
            innerRowDiv.appendChild(imgColDiv);
            innerRowDiv.appendChild(bodyColDiv);
            cardDiv.appendChild(innerRowDiv);
            colDiv.appendChild(cardDiv);
            rowDiv.appendChild(colDiv);

            const anchor = document.createElement('a');
            anchor.href = `http://localhost:8080/product/${product.id}`;
            anchor.style.textDecoration = 'none';
            anchor.style.color = 'inherit';
            anchor.appendChild(rowDiv);

            this.container.appendChild(anchor);
        });
    }

    getUserId() {
        return localStorage.getItem("userId") || 'guest';
    }
}