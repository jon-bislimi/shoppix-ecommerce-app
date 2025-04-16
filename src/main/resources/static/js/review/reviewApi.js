class ReviewApi {
    constructor() {
        this.baseUrl = "/api/reviews";
    }

    async addReview(review) {
        const response = await fetch(`${this.baseUrl}/add`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(review)
        });
        return response.json();
    }
    async updateReview(id, review) {
        const response = await fetch(`${this.baseUrl}/update/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(review)
        });
        return response.json();
    }
    async deleteReview(id) {
        const response = await fetch(`${this.baseUrl}/delete/${id}`, {
            method: 'DELETE'
        });
        return response.json();
    }
    async getReviewById(id) {
        const response = await fetch(`${this.baseUrl}/${id}`);
        return response.json();
    }
    async getAverageRatingByProductId(id) {
        const response = await fetch(`${this.baseUrl}/average-rating/${id}`);
        return response.json();
    }
    async getReviewsByUserId(id) {
        const response = await fetch(`${this.baseUrl}/user/${id}`);
        return response.json();
    }

    async getReviewsByUserIdAndProductId(userId, productId) {
        const response = await fetch(`${this.baseUrl}/user/${userId}/product/${productId}`);
        return response.json();
    }

    async getReviewsByProductId(id) {
        const response = await fetch(`${this.baseUrl}/product/${id}`);
        return response.json();
    }
}