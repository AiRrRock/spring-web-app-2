angular.module('market-front').controller('cartController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:8189/market/';

    $scope.getCartId = function () {
          $http({
            url: contextPath + 'api/v1/cart',
              method: 'GET'
             }).then(function (response) {
               $localStorage.cartId = response.data;
          });
    };

    $scope.loadCart = function () {
        if(!$localStorage.cartId){
            $scope.getCartId();
        }
        $http({
            url: contextPath + 'api/v1/cart/' + $localStorage.cartId ,
            method: 'GET'
        }).then(function (response) {
        window.locals
            $scope.cart = response.data;
        });
    };

    $scope.incrementItem = function (productId) {
        if(!$localStorage.cartId){
                    $scope.getCartId();
        }
        $http({
            url: contextPath +'api/v1/cart/'+ $localStorage.cartId +'/add/' + productId,
            method: 'GET'
        }).then(function (response) {
            $scope.loadCart();
        });
    };

    $scope.decrementItem = function (productId) {
        if(!$localStorage.cartId){
            $scope.getCartId();
        }
        $http({
            url: contextPath + 'api/v1/cart/'+ $localStorage.cartId +'/decrement/' + productId,
            method: 'GET'
        }).then(function (response) {
            $scope.loadCart();
        });
    };

    $scope.removeItem = function (productId) {
        if(!$localStorage.cartId){
            $scope.getCartId();
        }
        $http({
            url: contextPath + 'api/v1/cart/'+ $localStorage.cartId +'/remove/' + productId,
            method: 'GET'
        }).then(function (response) {
            $scope.loadCart();
        });
    };

    $scope.checkOut = function () {
        $location.path("/order_confirmation");
    }

    $scope.disabledCheckOut = function () {
        alert("Для оформления заказа необходимо войти в учетную запись");
    }

    $scope.loadCart();
});