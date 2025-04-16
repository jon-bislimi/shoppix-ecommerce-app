// window.fetch = ((originalFetch) => {
//     return function(url, options) {
//         if (typeof url === "string" && url.startsWith("http://localhost:8080/product/")) {
//             url = url.replace("http://localhost:8080/product/", "http://localhost:8080/");
//         }
//         return originalFetch(url, options);
//     };
// })(window.fetch);

class ProductPage {
    constructor() {
        this.productApi = new ProductApi();
        this.container = document.getElementById("containerDiv");
        console.log(this.container); // Check if the container is found
        if (!this.container) {
            console.error("Container element not found!");
            return;
        }
        this.userId = this.getUserId();
        console.log("User ID:", this.userId); // Log the user ID
        if (this.userId && this.userId !== 'guest') {
            this.userId = parseInt(this.userId);
        }
        this.url = window.location.href;
        console.log("URL:", this.url); // Log the URL
        //this.id = this.url.match(/\/product\/(\d+)/)[1];
        this.id = document.getElementById("productId").textContent;
        console.log("Product ID before:", this.id); // Log the ID
        this.id = parseInt(this.id);
        console.log("Product ID after:", this.id); // Log the ID
        console.log("Product ID:", this.id); // Log the ID
        this.wishlistApp = new WishlistApp();
        this.shoppingCartApi = new ShoppingCartApi();
        this.userApi = new UserApi();
        this.init();
    }

    async init() {
        console.log("ProductPage initialized");
        await this.loadProduct();
    }

    async loadProduct() {
        console.log("Loading product...");
        try {
            const product = await this.productApi.findById(this.id);
            console.log("Product fetched:", product);
            this.renderProduct(product);
        } catch (error) {
            console.error("Error loading product:", error);
        }
    }

    renderProduct(product) {
        console.log("Rendering product...");
        if (!product) {
            console.error("No product data available");
            return;
        }

        this.container.innerHTML = '';
        const rowDiv = document.createElement('div');
        rowDiv.className = 'row product-card';

        const colImgDiv = document.createElement('div');
        colImgDiv.className = 'col-md-6';

        const img = document.createElement('img');
        img.className = 'img-fluid';
        img.alt = 'Product Image';

        // Check if the product has images, if not, use a placeholder image
        if (product.images && product.images.length > 0) {
            img.src = product.images[0];
            console.log("Image URL:", img.src);
            if (img.src.includes('http://localhost:8080/product/products')) {
                img.src = img.src.replace(/\/product/, '')
            }
        } else {
            img.src = 'https://dummyimage.com/450x300/dee2e6/6c757d.jpg'; // Set a placeholder image URL
        }
        if (img.src === 'https://example.com/image.jpg') {
            img.src = 'https://dummyimage.com/450x300/dee2e6/6c757d.jpg';
        }

        colImgDiv.appendChild(img);

        const colDetailsDiv = document.createElement('div');
        colDetailsDiv.className = 'col-md-6 product-details';

        const h2 = document.createElement('h2');
        h2.textContent = product.name;

        const brandHeader = document.createElement('h4');
        brandHeader.textContent = product.brand;

        const descriptionP = document.createElement('p');
        descriptionP.textContent = product.description;

        const priceP = document.createElement('p');
        priceP.textContent = `Price: $${product.price}`;

        const stockP = document.createElement('p');
        stockP.textContent = `Stock: ${product.stock}`;

        const addToCartBtn = document.createElement('button');
        addToCartBtn.className = 'btn btn-primary';
        addToCartBtn.id = 'addToCartBtn';
        addToCartBtn.textContent = 'Add to Cart';

        const addToWishlistBtn = document.createElement('button');
        addToWishlistBtn.className = 'btn btn-secondary';
        addToWishlistBtn.id = 'addToWishlistBtn';
        addToWishlistBtn.textContent = 'Add to Wishlist';

        const buyProductBtn = document.createElement('button');
        buyProductBtn.id = 'buyProductBtn';
        buyProductBtn.className = 'btn btn-secondary';
        buyProductBtn.textContent = 'Buy Product!';

        addToWishlistBtn.addEventListener('click', async (e) => {
            e.preventDefault();
            const isProductInWishlist = await this.wishlistApp.isProductInWishlist(product.id);
            if (isProductInWishlist) {
                console.log(`Product ${product.id} is already in the wishlist`);
                alert("Product is already in the wishlist!");
                return;
            }
            await this.wishlistApp.addProductToWishlist(product.id);
            console.log(`Product ${product.id} added to wishlist`);
            alert("Product added to wishlist!");
        });

        addToCartBtn.addEventListener('click', async (e) => {
            e.preventDefault();
            if (this.userId === 'guest') {
                console.log("User is not logged in");
                alert("Please log in to add products to the cart!");
                window.location = 'http://localhost:8080/login';
                return;
            }
            console.log("Add to cart button clicked");
            console.log(`Product ${product.id} added to cart`);
            console.log("User ID:", this.userId);
            await this.shoppingCartApi.addProductToShoppingCart(this.userId, product.id);
            console.log(`Product ${product.id} added to cart`);
            alert("Product added to cart!");
        });

        buyProductBtn.addEventListener('click', async (e) => {
            e.preventDefault();

            if (!confirm("You are about to make a purchase for this product. Do you want to proceed?"))
                return

            if (this.userId === 'guest') {
                if (!confirm("You are not logged in, do you have a one time password?"))
                    return;

                console.log("User is not logged in");
                let nrTries = 0;
                while (nrTries < 3) {
                    nrTries++;
                    const oneTimePassword = prompt("Please enter your one time password (click Cancel to exit):");

                    // Check if user clicked Cancel or entered empty string
                    if (oneTimePassword === null) {
                        console.log("User cancelled OTP entry");
                        return;
                    }

                    if (oneTimePassword.trim()) {
                        try {
                            const tempId = await this.userApi.getUserByOtp(oneTimePassword);
                            console.log("Temporary user ID:", tempId);
                            if (!tempId) {
                                alert("Invalid one time password. Please try again.");
                                continue;
                            }
                            const result = await this.userApi.buyProduct(tempId, product.id);
                            if (result.success) {
                                alert("Purchase successful!");
                                window.location = 'http://localhost:8080/';
                            } else {
                                alert("Purchase failed. Please try again.");
                            }
                            return;
                        } catch (error) {
                            console.error("Error buying product:", error);
                            alert("Error buying product, please try again.");
                            return;
                        }
                    } else {
                        alert("Empty password. Please try again or click Cancel to exit.");
                    }
                }
                if (nrTries >= 3) {
                    alert("You've exhausted your attempts. Please try again later.");
                }
            }
            else if(this.userId && typeof this.userId === 'number') {
                console.log("Buy product button clicked");
                console.log(`Product ${product.id} bought`);
                console.log("User ID:", this.userId);
                try {
                    const result = await this.userApi.buyProduct(this.userId, product.id);
                    if (result.success) {
                        console.log("Purchase successful with status:", result.status);
                        alert("Purchase successful!");
                        // Optionally refresh product data to show updated stock
                        await this.loadProduct();
                    } else {
                        alert("Purchase failed. Please try again.");
                    }
                } catch (error) {
                    console.error("Error buying product:", error);
                    alert("Error buying product, please try again.");
                }
            }
        })

        colDetailsDiv.appendChild(h2);
        colDetailsDiv.appendChild(brandHeader);
        colDetailsDiv.appendChild(descriptionP);
        colDetailsDiv.appendChild(priceP);
        colDetailsDiv.appendChild(stockP);
        colDetailsDiv.appendChild(addToCartBtn);
        colDetailsDiv.appendChild(addToWishlistBtn);
        colDetailsDiv.appendChild(buyProductBtn);

        rowDiv.appendChild(colImgDiv);
        rowDiv.appendChild(colDetailsDiv);

        this.container.appendChild(rowDiv);
    }

    getUserId() {
        return localStorage.getItem('userId') || 'guest';
    }


}

document.addEventListener('DOMContentLoaded', () => {
    let productPage = new ProductPage();
});


class AdminActions {
    constructor() {
        this.editBtn = document.getElementById('editProductBtn');
        this.deleteBtn = document.getElementById('deleteProductBtn');
        this.productIdStr = document.getElementById('productId').textContent;
        this.productApi = new ProductApi();
    }

    async init() {
        console.log("AdminActions initialized");
        this.productId = parseInt(this.productIdStr);
        if (!this.editBtn || !this.deleteBtn) {
            console.error("Edit or delete button not found");
            return;
        }
        this.editBtn.addEventListener('click', async (e) => {
            e.preventDefault();
            console.log("Edit button clicked");
            await this.editProduct();
        });
        this.deleteBtn.addEventListener('click', async (e) => {
            e.preventDefault();
            if (!confirm("Are you sure you want to delete this product?")) {
                console.log("Delete button clicked");
                return;
            }
            console.log("Delete button clicked");
            await this.deleteProduct();
        });
    }

    async deleteProduct() {
        console.log("Deleting product...");
        try {
            await this.productApi.removeById(this.productId);
            console.log("Product deleted");
            window.location = 'http://localhost:8080/';
            alert("Product deleted!");
        } catch (error) {
            console.error("Error deleting product:", error);
        }
    }

    async editProduct() {
        console.log("Editing product...");
        window.location = 'http://localhost:8080/product/edit/' + this.productId;
    }
}
