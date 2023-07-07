<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Sales</title>
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
                        <li><a href="sales" class="nav-link px-2 text-secondary">Sales</a></li>
                        <li><a href="manage-products" class="nav-link px-2 text-white">Manage Products</a></li>
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
        <section class="py-3 text-center container">
            <div class="row py-lg-5">
                <div class="col-lg-6 col-md-8 mx-auto">
                    <h1 class="fw-light">KittyKouture</h1>
                    <p class="lead text-muted">I'm not kitten around, these sales are the cat's meow!</p>
                </div>
            </div>
        </section>
        <div class="card-body py-3 text-center container">
            <div class="dataTable-container">
                <#list sales as sale>
                    <div class="input-group flex-nowrap">
                        <input disabled type="text" class="form-control" placeholder="${sale.date}" aria-label="${sale.date}" aria-describedby="addon-wrapping">
                        <input disabled type="text" class="form-control" placeholder="${sale.user}" aria-label="${sale.user}" aria-describedby="addon-wrapping">
                    </div>
                    <table class="table datatable dataTable-table">
                        <thead>
                            <tr>
                                <th scope="col" style="width: 40%;"><p class="dataTable-sorter">Product</p></th>
                                <th scope="col" style="width: 20%;"><p class="dataTable-sorter">Price</p></th>
                                <th scope="col" style="width: 20%;"><p class="dataTable-sorter">Quantity</p></th>
                                <th scope="col" style="width: 20%;"><p class="dataTable-sorter">Total</p></th>
                            </tr>
                        </thead>
                        <tbody>
                        <#list sale.getProductQuantities() as product>
                            <tr>
                                <td>${product.getProduct().name}</td>
                                <td>${product.getProduct().price}</td>
                                <td>${product.quantity}</td>
                                <td>${product.getPrice()} $</td>
                            </tr>
                        </#list>
                            <tr>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td>${sale.totalPrice}</td>
                            </tr>
                        </tbody>
                    </table>
                    <br>
                </#list>
            </div>
        </div>
    </body>
</html>