class OrderApp {
    constructor() {
        this.orderApi = new OrderApi();
        this.orderItemApi = new OrderItemApi();
        this.productApi = new ProductApi();
        this.userId = this.getUserId();
    }

    async init() {
        try {
            await this.loadOrders();
        } catch (error) {
            this.showError('Failed to load orders');
        }
    }

    async loadOrders() {
        this.orders = await this.orderApi.getOrdersByUserId(this.userId);
        this.renderOrders(this.orders);
    }

    async renderOrders(orders) {
        const orderTable = document.getElementById('orders');
        if (!orderTable) {
            console.error('Element with ID "orders" not found');
            return;
        }
        orderTable.innerHTML = '';
        if (orders.length === 0) {
            const container = document.getElementById("container");
            container.innerHTML = '<h2>No orders found</h2>';
        }

        for (const order of orders) {
            const orderRow = document.createElement('li');
            orderRow.className = 'table-row';

            const orderIdDiv = document.createElement('div');
            orderIdDiv.className = 'col col-1';
            orderIdDiv.setAttribute('data-label', 'Order ID');
            orderIdDiv.textContent = order.id;

            const orderStatusDiv = document.createElement('div');
            orderStatusDiv.className = 'col col-2';
            orderStatusDiv.setAttribute('data-label', 'Order Status');
            orderStatusDiv.textContent = order.status;

            const orderItemsDiv = document.createElement('div');
            orderItemsDiv.className = 'col col-3';
            orderItemsDiv.setAttribute('data-label', 'Order Items');
            orderItemsDiv.textContent = '';

            const orderItemPromises = order.orderItemIds.map(id => this.orderItemApi.findById(id));
            const orderItems = await Promise.all(orderItemPromises);

            const productPromises = orderItems.map(item => this.productApi.findById(item.productId));
            const products = await Promise.all(productPromises);

            orderItems.forEach((orderItem, index) => {
                const product = products[index]; // Match product with order item
                const pTag = document.createElement('p');
                pTag.textContent = `${product.name} x ${orderItem.quantity}`;
                orderItemsDiv.appendChild(pTag);
            });

            const totalPriceDiv = document.createElement('div');
            totalPriceDiv.className = 'col col-4';
            totalPriceDiv.setAttribute('data-label', 'Total Price');
            totalPriceDiv.textContent = `$${order.totalPrice.toFixed(2)}`;

            orderRow.appendChild(orderIdDiv);
            orderRow.appendChild(orderStatusDiv);
            orderRow.appendChild(orderItemsDiv);
            orderRow.appendChild(totalPriceDiv);

            orderTable.appendChild(orderRow);
        }
    }

    showError(message) {
        alert(message);
    }
    getUserId() {
        return localStorage.getItem('userId') || 'guest';
    }
}

addEventListener("DOMContentLoaded", async () => {
    const orderApp = new OrderApp();
    await orderApp.init();
});
