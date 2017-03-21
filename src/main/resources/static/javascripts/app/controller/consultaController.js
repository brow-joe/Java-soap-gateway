app.controller('ConsultaController', function($scope, $window, DataEditor,
		ConsultaService) {

	$scope.register = function() {
		ConsultaService.edit(DataEditor, $window, null);
	};
	$scope.edit = function(wsdl) {
		ConsultaService.edit(DataEditor, $window, wsdl);
	};
	$scope.remove = function(wsdl) {
		remove(ConsultaService, $scope, wsdl);
	};

	findAll(ConsultaService, $scope);

	function remove(ConsultaService, $scope, wsdl) {
		ConsultaService.remove(wsdl, $scope).then(
				function(id) {
					if (id != null && id != 'undefined') {
						$scope.openModal('warning', 'Feito!', '',
								'Registro removido', false, false, '');
					}
					findAll(ConsultaService, $scope);
				});
	}

	function findAll(ConsultaService, $scope) {
		ConsultaService.findAll($scope).then(function(list) {
			$scope.wsdlList = list;
		});
	}
});