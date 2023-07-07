<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Register</title>
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
                    <ul class="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
                        <li><a href="products" class="nav-link px-2 text-white">Products</a></li>
                        <li><a href="shopping-cart" class="nav-link px-2 text-white">Shopping cart (${amount})</a></li>
                    </ul>
                    <div class="text-end">
                        <a href="login" class="btn btn-outline-light me-2">Login</a>
                    </div>
                </div>
            </div>
        </header>
        <div class="container">
            <div class="row">
                <div class="col-sm-9 col-md-7 col-lg-5 mx-auto">
                    <div class="card border-0 shadow rounded-3 my-5">
                        <div class="card-body p-4 p-sm-5">
                            <h5 class="card-title text-center mb-4 fw-light fs-3">Don't fur-get to register!</h5>
                            <form action="/register" method="post">
                                <div class="form-floating mb-3">
                                    <input name="username" type="text" class="form-control" id="username" placeholder="Username">
                                    <label for="username">Username</label>
                                </div>
                                <div class="form-floating mb-3">
                                    <input name="password" type="password" class="form-control" id="password" placeholder="Password">
                                    <label for="password">Password</label>
                                </div>
                                <br>
                                <div class="d-grid">
                                    <button type="submit" class="btn btn-outline-dark">Register</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>