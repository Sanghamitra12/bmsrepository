(function () {
    'use strict';

    angular
        .module('app', ['ngRoute', 'ngCookies'])
        .config(config)
        .run(run);

    config.$inject = ['$routeProvider', '$locationProvider'];
    function config($routeProvider, $locationProvider) {
        $routeProvider
            .when('/', {
                controller: 'HomeController',
                templateUrl: 'home/home.view.html',
                controllerAs: 'vm'
            })
             .when('/createlc', {
                controller: 'HomeController',
                templateUrl: 'home/createLC.html',
                controllerAs: 'vm'
            })
         /* .when('/lcStatus', {
                controller: 'HomeController',
                templateUrl: 'home/lcStatus.html',
                controllerAs: 'vm'
            })*/

            .when('/login', {
                controller: 'LoginController',
                templateUrl: 'login/login.view.html',
                controllerAs: 'vm'
            })

            .when('/register', {
                controller: 'RegisterController',
                templateUrl: 'register/register.view.html',
                controllerAs: 'vm'
            })
             .when('/viewlc', {
                controller: 'HomeController',
                templateUrl:  'home/viewLC.html',
                controllerAs: 'vm'
            })
           .when('/lchistory', {
                controller: 'HomeController',
                templateUrl: 'home/lcHistory.html',
                controllerAs: 'vm'
            })

            
            .when('/viewlcversion', {
                controller: 'HomeController',
                templateUrl: 'home/viewLcVersion.html',
                controllerAs: 'vm'
            })
            
             .when('/counterhome', {
                controller: 'counterPartyController',
                templateUrl: 'counterParty/home.html',
                controllerAs: 'vm'
            })
            .when('/viewcounterpartylc', {
                controller: 'counterPartyController',
                templateUrl: 'counterParty/viewCounterPartyLC.html',
                controllerAs: 'vm'
            })
            
            .when('/lchistorycp', {
                controller: 'counterPartyController',
                templateUrl: 'counterParty/lcHistoryCP.html',
                controllerAs: 'vm'
            })
            
            .when('/bankhome', {
                controller: 'issuingBankController',
                templateUrl: 'issuingBank/home.html',
                controllerAs: 'vm'
            })
            
             .when('/viewbanklc', {
                controller: 'issuingBankController',
                templateUrl: 'issuingBank/viewLCIssuingBank.html',
                controllerAs: 'vm'
            })
            
            
             .when('/lchistorybank', {
                controller: 'issuingBankController',
                templateUrl: 'issuingBank/issuingBankHistory.html',
                controllerAs: 'vm'
            })
            
            
            .otherwise({ redirectTo: '/login' });
    }

    run.$inject = ['$rootScope', '$location', '$cookies', '$http'];
    function run($rootScope, $location, $cookies, $http) {
        // keep user logged in after page refresh
        $rootScope.globals = $cookies.getObject('globals') || {};
        if ($rootScope.globals.currentUser) {
            $http.defaults.headers.common['Authorization'] = 'Basic ' + $rootScope.globals.currentUser.authdata;
        }

        $rootScope.$on('$locationChangeStart', function (event, next, current) {
            // redirect to login page if not logged in and trying to access a restricted page
            var restrictedPage = $.inArray($location.path(), ['/login', '/register']) === -1;
            var loggedIn = $rootScope.globals.currentUser;
            if (restrictedPage && !loggedIn) {
                $location.path('/login');
            }
        });
    }

})();