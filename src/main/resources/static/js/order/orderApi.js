class OrderApi extends BaseApi {
    constructor() {
        super("/api/orders");
    }

    async getOrdersByUserId(userId) {
        const response = await fetch(`${this.baseUrl}/user/${userId}`);
        return response.json();
    }
    async getOrdersByStatus(status) {
        const response = await fetch(`${this.baseUrl}/status/${status}`);
        return response.json();
    }
    async updateOrderStatus(orderId, status) {
        const response = await fetch(`${this.baseUrl}/${orderId}/status/${status}`, {
            method: 'PUT'
        });
        return response.json();
    }
    async getOrdersByDateRange(startDate, endDate) {
        const response = await fetch(`${this.baseUrl}/date-range?startDate=${startDate}&endDate=${endDate}`);
        return response.json();
    }
    async getOrdersByTotalPriceRange(minPrice, maxPrice) {
        const response = await fetch(`${this.baseUrl}/price-range?minPrice=${minPrice}&maxPrice=${maxPrice}`);
        return response.json();
    }
    async updateOrderAddressAndPhone(orderId, address, phone) {
        const response = await fetch(`${this.baseUrl}/${orderId}/address-phone`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ address: address, phone: phone })
        });
        return response.json();
    }
}