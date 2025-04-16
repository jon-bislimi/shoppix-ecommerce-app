class ProductApi extends BaseApi {
    constructor() {
        super("/api/products");
    }

    async getProductsByName(name) {
        const response = await fetch(`${this.baseUrl}/search?name=${name}`);
        return response.json();
    }

    async getProductsByCategory(categoryName) {
        console.log("categoryName", categoryName);
        const response = await fetch(`${this.baseUrl}/category/${categoryName}`);
        console.log(response);
        return response.json();
    }

    async getProductsByPriceRange(minPrice, maxPrice) {
        const response = await fetch(`${this.baseUrl}/price-range?minPrice=${minPrice}&maxPrice=${maxPrice}`);
        return response.json();
    }

    async getProductsByBrand(brand) {
        const response = await fetch(`${this.baseUrl}/brand?brand=${brand}`);
        return response.json();
    }

    async getProductsByRatingGreaterThanEqual(rating) {
        const response = await fetch(`${this.baseUrl}/rating?rating=${rating}`);
        return response.json();
    }

    async getProductsByAvailability(isAvailable) {
        const response = await fetch(`${this.baseUrl}/availability?isAvailable=${isAvailable}`);
        return response.json();
    }

    async getProductsCreatedAfter(date) {
        const response = await fetch(`${this.baseUrl}/created-after?date=${date}`);
        return response.json();
    }

    async getProductsWithStockGreaterThan(stock) {
        const response = await fetch(`${this.baseUrl}/stock-greater-than?stock=${stock}`);
        return response.json();
    }

    async getProductsBySubcategory(subcategoryName) {
        const response = await fetch(`${this.baseUrl}/subcategory/${subcategoryName}`);
        return response.json();
    }
    async getDto() {
        const response = await fetch(`${this.baseUrl}/get-dto`);
        return response.json();
    }
}