class WishlistApi extends BaseApi{
    constructor() {
        super("/api/wishlists");
    }
    async createWishlist(userId){
        const response = await fetch(`${this.baseUrl}/create/${userId}`, {
            method: 'POST'
        });
        return response.json();
    }
    async getWishlistByUserId(userId){
        const response = await fetch(`${this.baseUrl}/user/${userId}`);
        return response.json();
    }
    async addProductToWishlist(userId, productId){
        const response = await fetch(`${this.baseUrl}/${userId}/add-product/${productId}`, {
            method: 'POST'
        });
        return response.json();
    }
    async removeProductFromWishlist(userId, productId){
        const response = await fetch(`${this.baseUrl}/${userId}/remove-product/${productId}`, {
            method: 'DELETE'
        });
        return response.json();
    }
    async clearWishlist(wishlistId){
        const response = await fetch(`${this.baseUrl}/${wishlistId}/clear`, {
            method: 'DELETE'
        });
        return response.json();
    }
    async isProductInWishlist(userId, productId){
        const response = await fetch(`${this.baseUrl}/${userId}/contains/${productId}`);
        return response.json();
    }
    async buyWishlistProducts(wishlistId){
        const response = await fetch(`${this.baseUrl}/${wishlistId}/buy`, {
            method: 'POST'
        });
        return response.json();
    }
}