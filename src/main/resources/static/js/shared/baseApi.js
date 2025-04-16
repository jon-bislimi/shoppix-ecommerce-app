class BaseApi {

    constructor(baseUrl) {
        this.firstUrl = "http://localhost:8080";
        this.baseUrl = this.firstUrl + baseUrl;
    }

    async findAll() {
        const response = await fetch(`${this.baseUrl}/all`);
        return response.json();
    }

    async findById(id) {
        const response = await fetch(`${this.baseUrl}/${id}`);
        return response.json();
    }

    async add(entity){
        const response = await fetch(`${this.baseUrl}/add`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(entity)
        });
        return response.json();
    }

    async update(id, entity) {
        const response = await fetch(`${this.baseUrl}/update/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(entity)
        });
        return response.json();
    }

    async removeById(id){
        const response = await fetch(`${this.baseUrl}/delete/${id}`, {
            method: 'DELETE'
        });
        return response.json
    }

}



