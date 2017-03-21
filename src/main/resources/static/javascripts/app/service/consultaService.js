app.service('ConsultaService', function(ConsultaFactory) {
	this.findAll = function($scope) {
		return ConsultaFactory.findAll($scope);
	};

	this.edit = function(DataEditor, $window, wsdl) {
		return toRegister(DataEditor, $window, wsdl);
	};

	this.remove = function(wsdl, $scope) {
		return ConsultaFactory.remove(wsdl, $scope);
	};

	function toRegister(DataEditor, $window, wsdl) {
		DataEditor.Data = wsdl;
		$window.location.href = '#!/register';
	}
});