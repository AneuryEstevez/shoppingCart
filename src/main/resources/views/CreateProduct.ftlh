<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Create Product</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
        <script src="index.js"></script>
    </head>
    <body>
    <#if user??>
        <input id="user" type="hidden" value="${user}">
    </#if>
        <header class="p-3 text-bg-dark">
            <div class="container">
                <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
                    <a href="/" class="d-flex align-items-center mb-2 mb-lg-0 text-white text-decoration-none">
                        <svg class="bi me-2" width="40" height="32" role="img" aria-label="Bootstrap"><use xlink:href="#bootstrap"></use></svg>
                    </a>
                    <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
                        <li><a href="products" class="nav-link px-2 text-white">Products</a></li>
                        <li><a href="sales" class="nav-link px-2 text-white">Sales</a></li>
                        <li><a href="manage-products" class="nav-link px-2 text-secondary">Manage Products</a></li>
                        <li><a href="shopping-cart" class="nav-link px-2 text-white">Shopping cart (${amount})</a></li>
                        <li><a href="/dashboard" class="nav-link px-2 text-white">Dashboard</a></li>
                        <li>
                            <a class="nav-link px-2 text-white">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-people-fill" viewBox="0 0 16 16">
                                    <path d="M7 14s-1 0-1-1 1-4 5-4 5 3 5 4-1 1-1 1H7Zm4-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6Zm-5.784 6A2.238 2.238 0 0 1 5 13c0-1.355.68-2.75 1.936-3.72A6.325 6.325 0 0 0 5 9c-4 0-5 3-5 4s1 1 1 1h4.216ZM4.5 8a2.5 2.5 0 1 0 0-5 2.5 2.5 0 0 0 0 5Z"></path>
                                </svg>
                                <span id="quantity">0</span>
                            </a>
                        </li>
                    </ul>
                    <div class="text-end">
                        <a class="btn btn-white" style="display: inline-block;">${user}</a>
                        <a href="login/logout" class="btn btn-dark">Logout</a>
                    </div>
                </div>
            </div>
        </header>
        <div class="container">
            <div class="row">
                <div class="col-sm-9 col-md-7 col-lg-5 mx-auto">
                    <div class="card border-0 shadow rounded-3 my-5">
                        <div class="card-body p-4 p-sm-5">
                            <h5 class="card-title text-center mb-4 fw-light fs-3">Let's put our paws together and create something amazing!</h5>
                            <form action="/create-product" method="post" enctype="multipart/form-data">
                                <div class="form-floating mb-3">
                                    <input name="name" type="text" class="form-control" id="name" placeholder="Name">
                                    <label for="name">Name</label>
                                </div>
                                <div class="form-floating mb-3">
                                    <select name="category" class="form-select" id="category" aria-label="Floating label select example">
                                        <option>Open this select menu</option>
                                        <#assign categories =[
                                        'Food',
                                        'Litter & Litter Boxes',
                                        'Treats',
                                        'Bowls & Feeders',
                                        'Leashes, Collars & ID Tags',
                                        'Toys',
                                        'Grooming Supplies',
                                        'Clothing & Accessories']>
                                        <#list categories as category>
                                            <option value="${category}">${category}</option>
                                        </#list>
                                    </select>
                                    <label for="category">Category</label>
                                </div>
                                <div class="form-floating mb-3">
                                    <textarea name="description" class="form-control" placeholder="Leave a description here" id="floatingTextarea2" style="height: 150px"></textarea>
                                    <label for="floatingTextarea2">Description</label>
                                </div>
                                <div class="form-floating mb-3">
                                    <input name="price" type="number" class="form-control" id="floatingPrice" placeholder="Price" step=".01" min="1">
                                    <label for="floatingPrice">Product Price</label>
                                </div>
                                <div class="mb-3">
                                    <input name="image" class="form-control" type="file" id="image" multiple>
                                    <br>
                                    <small>NOTE: To select multiple files, hold down the CTRL or SHIFT key while selecting</small>
                                </div>
                                <div class="d-grid">
                                    <div class="btn-group">
                                        <button type="submit" class="btn btn-outline-dark">Create</button>
                                        <a href="manage-products" type="button" class="btn btn-outline-dark">Cancel</a>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>