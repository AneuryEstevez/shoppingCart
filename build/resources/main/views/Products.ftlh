<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Products</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">

        <link href="https://getbootstrap.com/docs/5.3/assets/css/docs.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
        <script>
            let webSocket;

            document.addEventListener("DOMContentLoaded", function(){
                connect();
            })

            function connect() {

                // if (document.querySelector('#user')) {
                    webSocket = new WebSocket("ws://localhost:7070/shoppingCart");

                    webSocket.onopen = function () {
                        console.log("conectado a web socket");
                        if (document.querySelector("#myChart")) {
                            webSocket.send(JSON.stringify({
                                type:"InitProductsSold"
                            }));
                        }
                        if (document.querySelector("#user")) {
                            webSocket.send(JSON.stringify({
                                type:"usuarioLogueado"
                            }));
                        }
                    }

                    webSocket.onclose = function () {
                        console.log("desconectado")
                    }

                    webSocket.addEventListener("message", (event) => {
                        var data = JSON.parse(event.data);
                        if (data.type == "cantidadUsuarios") {
                            updateCount(data.cantidad);
                        } else if (data.type == "getProductsSold") {
                            productsSold(data.products);
                        } else if (data.type == "deleteComment") {
                            const comment = document.getElementById(data.idComment);
                            comment.style.display = "none";
                            console.log(data.idComment);
                        }
                    });
                // }
            }

            function updateCount(msg) {
                document.getElementById("quantity").innerHTML = msg;
            }

            function productsSold(msg) {
                var valor = [];
                var label = [];
                var colores = [];

                if (myChart != null) {
                    Object.keys(msg).forEach(key => {
                        console.log(key, msg[key]);
                        valor.push(msg[key]);
                        label.push(key);
                        colores.push('#'+Math.floor(Math.random()*16777215).toString(16));
                    });
                    myChart.data.datasets[0].data = valor;
                    myChart.data.labels = label;
                    myChart.data.datasets[0].backgroundColor = colores;
                    myChart.update();
                }
            }
        </script>
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
                                <li><a href="/products" class="nav-link px-2 text-secondary">Products</a></li>
                                <li><a href="/sales" class="nav-link px-2 text-white">Sales</a></li>
                                <li><a href="/manage-products" class="nav-link px-2 text-white">Manage Products</a></li>
                                <li><a href="/shopping-cart" class="nav-link px-2 text-white">Shopping cart (${amount})</a></li>
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
                                <a href="/login/logout" class="btn btn-dark">Logout</a>
                            </div>
                        <#else>
                            <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
                                <li><a href="/products" class="nav-link px-2 text-secondary">Products</a></li>
                                <li><a href="/shopping-cart" class="nav-link px-2 text-white">Shopping cart (${amount})</a></li>
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
                                <a href="/login/logout" class="btn btn-dark">Logout</a>
                            </div>
                        </#if>
                    <#else>
                        <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
                            <li><a href="/products" class="nav-link px-2 text-secondary">Products</a></li>
                            <li><a href="/shopping-cart" class="nav-link px-2 text-white">Shopping cart (${amount})</a></li>
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
                            <a href="/login" class="btn btn-outline-light me-2">Login</a>
                            <a href="/register" class="btn btn-dark">Sing-up</a>
                        </div>
                    </#if>
                </div>
            </div>
        </header>
        <section class="py-3 text-center container">
            <div class="row py-lg-5">
                <div class="col-lg-6 col-md-8 mx-auto">
                    <h1 class="fw-light">KittyKouture</h1>
                    <p class="lead text-muted">Accessorize your cat-titude!</p>
                </div>
            </div>
        </section>
        <#if mainProduct??>
            <div class="container mb-3">
                <div class="row row-cols-1 row-cols-sm-2 row-cols-md-2 g-3">
                    <div class="col">
                        <div class="card shadow-sm">
                            <a href="/products">
                                <#if (mainProduct.images)?has_content>
                                    <#assign x=mainProduct.imagesSize()>
                                    <div id="carouselExample" class="carousel slide">
                                        <div class="carousel-inner">
                                            <#list 1..x as i>
                                                <div class="carousel-item active">
                                                    <#assign result = "data:image/png;base64,"+mainProduct.images[i-1].getSrc()>
                                                    <img src="${result}" class="card-img-top" alt="..." height="225">
                                                </div>
                                            </#list>
                                        </div>
                                        <button class="carousel-control-prev" type="button" data-bs-target="#carouselExample" data-bs-slide="prev">
                                            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                            <span class="visually-hidden">Previous</span>
                                        </button>
                                        <button class="carousel-control-next" type="button" data-bs-target="#carouselExample" data-bs-slide="next">
                                            <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                            <span class="visually-hidden">Next</span>
                                        </button>
                                    </div>
                                <#else>
                                    <svg class="bd-placeholder-img card-img-top" width="100%" height="225" xmlns="http://www.w3.org/2000/svg" role="img" aria-label="Placeholder: Thumbnail" preserveAspectRatio="xMidYMid slice" focusable="false">
                                        <title>Placeholder</title>
                                        <rect width="100%" height="100%" fill="#55595c"></rect>
                                    </svg>
                                </#if>
                            </a>
                            <div class="card-body">
                                <p class="card-text">${mainProduct.name}</p>
                                <small class="text-muted">${mainProduct.category}</small>
                                <p></p>
                                <form action="/products/${mainProduct.id}" method="post">
                                    <div class="form-floating mb-3">
                                        <input name="quantity" type="number" class="form-control" id="quantity" placeholder="Quantity" min="1" value="1">
                                        <label for="quantity">Quantity</label>
                                    </div>
                                    <div class="d-flex justify-content-between align-items-center">
                                        <button type="submit" class="btn btn-sm btn-outline-secondary">Add to cart</button>
                                        <small class="text-muted">${mainProduct.price} $</small>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                    <div class="col">
                        <div class="card shadow-sm" style="height: 443px">
                            <div class="card-body">
                                <p data-bs-spy="scroll" data-bs-offset="0" class="scrollspy-example" tabindex="0" style="height: 50%">${mainProduct.description}</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="container mb-3">
                <div class="row row-cols-1 row-cols-sm-1 row-cols-md-1 g-3">
                    <#if user??>
                        <div class="col">
                            <div class="card shadow-sm">
                                <div class="card-body">
                                    <form action="/products/${mainProduct.id}/${user}" method="post">
                                        <div class="form-floating mb-3">
                                            <textarea name="comment" class="form-control" placeholder="Leave a description here" id="floatingTextarea2" style="height: 75px; max-height: 75px;  min-height: 75px"></textarea>
                                            <label for="floatingTextarea2">Comment</label>
                                        </div>
                                        <div class="d-flex justify-content-between align-items-center">
                                            <button type="submit" class="btn btn-sm btn-outline-secondary">Submit</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    <#else>
                        <div class="col">
                            <div class="card shadow-sm">
                                <div class="card-body">
                                    <div class="form-floating mb-3">
                                        <textarea name="comment" class="form-control" placeholder="Leave a comment here" id="floatingTextarea2" style="height: 75px; max-height: 75px;  min-height: 75px" ></textarea>
                                        <label for="floatingTextarea2">Comment</label>
                                    </div>
                                    <div class="d-flex justify-content-between align-items-center">
                                        <a href="/register" class="btn btn-sm btn-outline-secondary">Submit</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </#if>
                    <#if comments??>
                        <#list comments as comment>
                            <#if mainProduct??>
                                <#if comment.productId == mainProduct.id>
                                    <div class="col">
                                        <div class="card shadow-sm" id="${comment.id}">
                                            <div class="card-body">
                                                <div class="d-flex justify-content-between align-items-center">
                                                    <p>${comment.text}</p>
                                                    <small>${comment.date}</small>
                                                </div>
                                                <#if user??>
                                                    <#if user == "admin">
                                                        <div class="d-flex justify-content-between align-items-center">
                                                            <small>${comment.user}</small>
                                                            <a href="/product/${mainProduct.id}/${comment.id}" class="btn btn-sm btn-outline-danger deleteComment">Delete</a>
                                                        </div>
                                                    </#if>
                                                <#else>
                                                    <small>${comment.user}</small>
                                                </#if>
                                            </div>
                                        </div>
                                    </div>
                                </#if>
                            </#if>
                        </#list>
                    </#if>
                </div>
            </div>
        </#if>
        <#if products??>
            <#if mainProduct??>
            <#else>
                <div class="container">
                    <div class="row row-cols-1 row-cols-sm-2 row-cols-md-4 g-3">
                        <#list products as product>
                            <div class="col">
                                <div class="card shadow-sm">
                                    <a href="/product/${product.id}">
                                        <#if (product.images)?has_content>
                                            <#assign x=product.imagesSize()>
                                            <div id="carouselExample" class="carousel slide">
                                                <div class="carousel-inner">
                                                    <#list 1..x as i>
                                                        <div class="carousel-item active">
                                                            <#assign result = "data:image/png;base64,"+product.images[i-1].getSrc()>
                                                            <img src="${result}" class="card-img-top" alt="..." height="225">
                                                        </div>
                                                    </#list>
                                                </div>
                                                <button class="carousel-control-prev" type="button" data-bs-target="#carouselExample" data-bs-slide="prev">
                                                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                                    <span class="visually-hidden">Previous</span>
                                                </button>
                                                <button class="carousel-control-next" type="button" data-bs-target="#carouselExample" data-bs-slide="next">
                                                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                                    <span class="visually-hidden">Next</span>
                                                </button>
                                            </div>
                                        <#else>
                                            <svg class="bd-placeholder-img card-img-top" width="100%" height="225" xmlns="http://www.w3.org/2000/svg" role="img" aria-label="Placeholder: Thumbnail" preserveAspectRatio="xMidYMid slice" focusable="false">
                                                <title>Placeholder</title>
                                                <rect width="100%" height="100%" fill="#55595c"></rect>
                                            </svg>
                                        </#if>
                                    </a>
                                    <div class="card-body">
                                        <p class="card-text">${product.name}</p>
                                        <small class="text-muted">${product.category}</small>
                                        <p></p>
                                        <form action="/products/${product.id}" method="post">
                                            <div class="form-floating mb-3">
                                                <input name="quantity" type="number" class="form-control" id="quantity" placeholder="Quantity" min="1" value="1">
                                                <label for="quantity">Quantity</label>
                                            </div>
                                            <div class="d-flex justify-content-between align-items-center">
                                                <button type="submit" class="btn btn-sm btn-outline-secondary">Add to cart</button>
                                                <small class="text-muted">${product.price} $</small>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </#list>
                    </div>
                </div>
                <br>
                <nav aria-label="Page navigation example" class="d-flex justify-content-center">
                    <ul class="pagination">
                        <#list 1..pagination.totalPages as page>
                            <#if page == pagination.currentPage>
                                <a class="page-link" class="text-dark">${page}</a>
                            <#else>
                                <a class="page-link text-dark" href="?page=${page}">${page}</a>
                            </#if>
                        </#list>
                    </ul>
                </nav>
            </#if>
        </#if>
    </body>
</html>