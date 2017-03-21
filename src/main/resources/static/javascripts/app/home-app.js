var host_app = 'http://localhost:8080/api';
var app = angular.module('home_app', [ 'ngRoute' ]);

app.config(function($routeProvider) {
	$routeProvider.when('/consult', {
		templateUrl : 'consulta.html',
		controller : 'ConsultaController'
	}).when('/register', {
		templateUrl : 'cadastro.html',
		controller : 'CadastroController'
	}).when('/messages', {
		templateUrl : 'mensagens.html',
		controller : 'MensagemController'
	}).when('/test', {
		templateUrl : 'teste.html',
		controller : 'TesteController'
	}).otherwise({
		redirectTo : '/consult'
	});
});

app.controller('HomeController', function($scope, $window, $interval,
		TesteService) {
	$scope.test = function(data, location) {
		$scope.executor = data;
		$scope.location = '#!/' + location;
		$window.location.href = '#!/test';
	};

	$scope.toMessages = function() {
		$window.location.href = '#!/messages';
	};

	configureModalDialog($scope);

	var updateMessages = function() {
		TesteService.findAllMessages($scope).then(function(list) {
			$scope.messages = list;
		});
	};

	updateMessages();
	$interval(updateMessages, 5000);
});

app
		.directive(
				'modal',
				function() {
					return {
						template : '<div class="modal fade">'
								+ '<div class="modal-dialog">'
								+ '<div class="modal-content">'
								+ '<div class="modal-header alert-{{modaltype}}">'
								+ '<button type="button" class="close" data-dismiss="modal" aria-hidden="true">'
								+ '<span class="glyphicon glyphicon-remove-circle"></span>'
								+ '</button>'
								+ '<strong>{{modalheader}}</strong> {{modalmessage}}'
								+ '</div>'
								+ '<div ng-if="modalSimple">'
								+ '<div class="modal-body">'
								+ '<p>{{modalbody}}</p>'
								+ '<div ng-if="modalJson">'
								+ '<textarea rows=10 class="form-control" disabled>{{ modalBodyjson }}</textarea>'
								+ '</div>'
								+ '</div>'
								+ '<div class="modal-footer">'
								+ '<button type="button" class="btn btn-default" data-dismiss="modal">Fechar</button>'
								+ '</div>' + '</div>' + '</div>' + '</div>'
								+ '</div>',
						restrict : 'E',
						transclude : true,
						replace : true,
						scope : true,
						link : function postLink(scope, element, attrs) {
							scope.title = attrs.title;

							scope.$watch(attrs.visible, function(value) {
								if (value == true)
									$(element).modal('show');
								else
									$(element).modal('hide');
							});

							$(element).on('shown.bs.modal', function() {
								scope.$apply(function() {
									scope.$parent[attrs.visible] = true;
								});
							});

							$(element).on('hidden.bs.modal', function() {
								scope.$apply(function() {
									scope.$parent[attrs.visible] = false;
								});
							});
						}
					};
				});

function configureModalDialog($scope) {
	$scope.showModal = false;
	$scope.modaltype = '';
	$scope.modalheader = '';
	$scope.modalmessage = '';
	$scope.modalbody = '';
	$scope.modalSimple = false;
	$scope.modalJson = false;
	$scope.modalBodyjson = '';
	$scope.toggleModal = function() {
		$scope.showModal = !$scope.showModal;
	};
	$scope.openModal = function(modaltype, modalheader, modalbody,
			modalmessage, modalSimple, modalJson, modalBodyjson) {
		$scope.modaltype = modaltype;
		$scope.modalheader = modalheader;
		$scope.modalmessage = modalmessage;
		$scope.modalbody = modalbody;
		$scope.modalSimple = modalSimple;
		$scope.modalJson = modalJson;
		$scope.modalBodyjson = modalBodyjson;
		$scope.toggleModal();
	};
}