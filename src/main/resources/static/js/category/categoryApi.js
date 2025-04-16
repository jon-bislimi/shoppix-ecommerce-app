class CategoryApi extends BaseApi {
    constructor() {
        super("/api/categories");
    }
    async getCategoryByName(name) {
        const response = await fetch(`${this.baseUrl}/name/${name}`);
        return response.json();
    }
    async getAllSubcategories() {
        const response = await fetch(`${this.baseUrl}/subcategories/all`);
        return response.json();
    }

    async getSubcategoryById(id) {
        const response = await fetch(`${this.baseUrl}/subcategories/${id}`);
        return response.json();
    }
    async saveSubcategory(subcategory) {
        const response = await fetch(`${this.baseUrl}/subcategories/add`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(subcategory)
        });
        return response.json();
    }

    async deleteSubcategory(id) {
        const response = await fetch(`${this.baseUrl}/subcategories/delete/${id}`, {
            method: 'DELETE'
        });
        return response.json();
    }

    async updateSubcategoryById(id, subcategory) {
        const response = await fetch(`${this.baseUrl}/subcategories/update/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(subcategory)
        });
        return response.json();
    }

    async getSubcategoryByName(name) {
        const response = await fetch(`${this.baseUrl}/subcategories/name/${name}`);
        return response.json();
    }
    async getDto() {
        const response = await fetch(`${this.baseUrl}/get-dto`);
        return response.json();
    }

    async getSubcategoryDto() {
        const response = await fetch(`${this.baseUrl}/subcategories/get-dto`);
        return response.json();
    }
}