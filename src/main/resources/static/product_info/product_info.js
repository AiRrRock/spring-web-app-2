angular.module('market-front').controller('productInfoController', function ($scope, $http, $routeParams, $location) {
    const contextPath = 'http://localhost:8189/market/';

    $scope.loadProduct = function () {
        $http.get(contextPath + 'api/v1/products/' + $routeParams.productId)
            .then(function successCallback (response) {
                $scope.product = response.data;
                console.log($scope.product);
            }, function failureCallback (response) {
                console.log(response);
                alert(response.data.messages);
                $location.path('/store');
            });

         $http.get(contextPath + 'api/v1/comments/' + $routeParams.productId)
                    .then(function successCallback (response) {
                        $scope.comments = response.data;
                    }, function failureCallback (response) {
                        console.log(response);
                        alert(response.data.messages);
                        $location.path('/store');
                    });
    }

        $scope.addComment = function () {
            $http.post(contextPath + 'api/v1/comments/', $scope.comment)
                .then(function successCallback (response) {
                    $scope.comment = null;
                }, function failureCallback (response) {
                    alert(response.data.messages);
                });
        }

        $scope.prepareComment = function () {
            $http.get(contextPath + 'api/v1/comments/prepare/' + $routeParams.productId)
                .then(function successCallback (response) {
                    $scope.comment = response.data;
                    console.log($scope.comment);
                }, function failureCallback (response) {
                    console.log(response);
                    alert(response.data.messages);
                    $location.path('/store');
                });
        }
    $scope.prepareComment();
    $scope.loadProduct();
});