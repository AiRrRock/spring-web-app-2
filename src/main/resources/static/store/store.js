angular.module('market-front').controller('storeController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:8189/market/';
    let currentPageIndex = 1;

    $scope.getCartId = function () {
          $http({
            url: contextPath + 'api/v1/cart',
            method: 'GET'
          }).then(function (response) {
             console.log(response)
             $localStorage.cartId = response.data;
          });
    };

    $scope.loadProducts = function (pageIndex = 1) {
        currentPageIndex = pageIndex;
        $http({
            url: contextPath + 'api/v1/products',
            method: 'GET',
            params: {
                p: pageIndex
            }
        }).then(function (response) {
            console.log(response);
            $scope.productsPage = response.data;
            $scope.paginationArray = $scope.generatePagesIndexes(1, $scope.productsPage.totalPages);
        });
    };

    $scope.addToCart = function (productId) {
        if(!$localStorage.cartId){
           $scope.getCartId();
        }
        $http({
            url: contextPath + 'api/v1/cart/'+ $localStorage.cartId +'/add/' + productId,
            method: 'GET'
        }).then(function (response) {
        });
    };

    $scope.generatePagesIndexes = function (startPage, endPage) {
        let arr = [];
        for (let i = startPage; i < endPage + 1; i++) {
            arr.push(i);
        }
        return arr;
    }

    $scope.navToEditProductPage = function (productId) {
        $location.path('/edit_product/' + productId);
    }

    $scope.loadProducts();
});