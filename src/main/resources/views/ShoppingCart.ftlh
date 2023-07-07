<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Shopping Cart</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
        <script src="index.js"></script>
    </head>
    <body>
        <header class="p-3 text-bg-dark">
            <div class="container">
                <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
                    <a href="/" class="d-flex align-items-center mb-2 mb-lg-0 text-white text-decoration-none">
                        <svg class="bi me-2" width="40" height="32" role="img" aria-label="Bootstrap"><use xlink:href="#bootstrap"></use></svg>
                    </a>
                    <#if user??>
                        <input id="user" type="hidden" value="${user}">
                        <#if user == "admin">
                            <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
                                <li><a href="products" class="nav-link px-2 text-white">Products</a></li>
                                <li><a href="sales" class="nav-link px-2 text-white">Sales</a></li>
                                <li><a href="manage-products" class="nav-link px-2 text-white">Manage Products</a></li>
                                <li><a href="shopping-cart" class="nav-link px-2 text-secondary">Shopping cart (${amount})</a></li>
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
                        <#else>
                            <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
                                <li><a href="products" class="nav-link px-2 text-white">Products</a></li>
                                <li><a href="shopping-cart" class="nav-link px-2 text-secondary">Shopping cart (${amount})</a></li>
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
                        </#if>
                    <#else>
                        <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
                            <li><a href="products" class="nav-link px-2 text-white">Products</a></li>
                            <li><a href="shopping-cart" class="nav-link px-2 text-secondary">Shopping cart (${amount})</a></li>
                        </ul>
                        <div class="text-end">
                            <a href="login" class="btn btn-outline-light me-2">Login</a>
                            <a href="register" class="btn btn-dark">Sing-up</a>
                        </div>
                    </#if>
                </div>
            </div>
        </header>
        <#if products??>
            <section class="py-3 text-center container">
                <div class="row py-lg-5">
                    <div class="col-lg-6 col-md-8 mx-auto">
                        <h1 class="fw-light">KittyKouture</h1>
                        <p class="lead text-muted">We're not kitten around when we say these products are great.</p>
                    </div>
                </div>
            </section>
            <div class="container">
                <div class="input-group mb-3">
                    <div class="form-floating">
                        <input disabled name="totalPrice" type="text" class="form-control" id="totalPrice" placeholder="${totalPrice}" value="${totalPrice}">
                        <label for="totalPrice">Total:</label>
                    </div>
                    <a href="purchase/${totalPrice}" class="btn btn-sm btn-outline-success" id="totalPricelabel">Purchase</a>
                </div>
                <div class="row row-cols-1 row-cols-sm-2 row-cols-md-4 g-3">
                    <#list products as product>
                        <div class="col">
                            <div class="card shadow-sm">
                                <div class="card-body">
                                    <p>${product.getProduct().name}</p>
                                    <small class="text-muted">${product.getProduct().category}</small>
                                    <p></p>
                                    <div class="form-floating mb-3">
                                        <input disabled name="price" type="text" class="form-control" id="price" placeholder="${product.getProduct().price}" value="${product.getProduct().price}">
                                        <label for="price">Price</label>
                                    </div>
                                    <div class="form-floating mb-3">
                                        <input disabled name="quantity" type="text" class="form-control" id="quantity" placeholder="${product.quantity}" value="${product.quantity}">
                                        <label for="quantity">Quantity</label>
                                    </div>
                                    <div class="d-flex justify-content-between align-items-center">
                                        <div class="btn-group">
                                            <a href="shopping-cart/${product.id}" class="btn btn-sm btn-outline-danger">Remove</a>
                                        </div>
                                        <small class="text-muted">${product.getPrice()} $</small>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </#list>
                </div>
            </div>
        <#else>
            <section class="py-3 text-center container">
                <div class="row py-lg-5">
                    <div class="col-lg-6 col-md-8 mx-auto">
                        <h1 class="fw-light">KittyKouture</h1>
                        <p class="lead text-muted">I thought I saw a cat in that box, but it was just empty-purrmptied.</p>
                    </div>
                </div>
            </section>
        </#if>
    </body>
</html>