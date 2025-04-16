// class CreateForm {
//     constructor() {
//         this.url = window.location.href;
//         this.dto = this.url.substring(this.url.lastIndexOf('/') + 1);
//         this.productApi = new ProductApi();
//         this.categoryApi = new CategoryApi();
//         this.form = document.getElementsByClassName("createForm").item(0);
//         this.url = window.location.href;
//         this.dto = this.url.substring(this.url.lastIndexOf('/') + 1);
//
//         this.init();
//         this.setupFormSubmit();
//     }
//
//     async init() {
//         console.log("CreateForm initialized");
//         if (this.dto === 'product') {
//             await this.loadCategories();
//             await this.loadSubcategories();
//         }
//         if (this.dto === 'subcategory')
//             await this.loadParentCategories();
//     }
//
//     async loadCategories() {
//         console.log("Loading categories...");
//         const categories = await this.categoryApi.findAll();
//         console.log("Categories:", categories);
//         this.renderCategories(categories);
//     }
//
//     async loadParentCategories() {
//         console.log("Loading parent categories...");
//         const categories = await this.categoryApi.findAll();
//         console.log("Parent Categories:", categories);
//         this.renderParentCategories(categories);
//     }
//
//     renderCategories(categories) {
//         console.log("Rendering categories...");
//         this.categorySelect = document.getElementById('categoryId');
//         this.categorySelect.innerHTML = '<option value="">Select Category</option>';
//
//         categories.forEach(category => {
//             const option = document.createElement('option');
//             option.value = category.id;
//             option.text = category.name;
//             this.categorySelect.appendChild(option);
//         });
//
//         console.log("Category dropdown after rendering:", this.categorySelect.innerHTML);
//     }
//
//     renderParentCategories(categories) {
//         console.log("Rendering parent categories...");
//         this.parentCategorySelect = document.getElementById('parentCategoryId');
//         this.parentCategorySelect.innerHTML = '<option value="">Select Parent Category</option>';
//
//         categories.forEach(category => {
//             const parentOption = document.createElement('option');
//             parentOption.value = category.id;
//             parentOption.text = category.name;
//             this.parentCategorySelect.appendChild(parentOption);
//         });
//
//         console.log("Parent Category dropdown after rendering:", this.parentCategorySelect.innerHTML);
//     }
//
//     async loadSubcategories() {
//         console.log("Loading subcategories...");
//         const subcategories = await this.categoryApi.getAllSubcategories();
//         this.renderSubcategories(subcategories);
//     }
//
//     renderSubcategories(subcategories) {
//         console.log("Rendering subcategories...");
//         this.subcategorySelect = document.getElementById('subcategoryId');
//         this.subcategorySelect.innerHTML = '<option value="">Select Subcategory</option>';
//
//         subcategories.forEach(subcategory => {
//             const option = document.createElement('option');
//             option.value = subcategory.id;
//             option.text = subcategory.name;
//             this.subcategorySelect.appendChild(option);
//         });
//     }
//
//     setupFormSubmit() {
//         this.form.addEventListener('submit', async (event) => {
//             event.preventDefault();
//
//             const formData = {
//                 name: document.getElementById('name').value,
//                 description: document.getElementById('description').value,
//                 price: document.getElementById('price').value,
//                 stock: document.getElementById('stock').value,
//                 categoryId: this.categorySelect.value || null,
//                 brand: document.getElementById('brand').value
//             };
//
//             console.log("Final form data before submission:", formData);
//
//             try {
//                 const response = await this.productApi.add(formData);
//                 console.log("Response:", response);
//             } catch (error) {
//                 console.error("Error submitting form:", error);
//             }
//         });
//     }
// }
//
// document.addEventListener('DOMContentLoaded', () => {
//     new CreateForm();
// });