console.log("js is running")
class HomeApp {
    constructor() {
        this.productApp = new ProductApp();
        this.loaderDiv = document.getElementById("homeLoader");
        this.init();
    }

    async init() {

    }
}

let homeApp = new HomeApp();

