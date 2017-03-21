app.service('CadastroService', function(CadastroFactory, TesteFactory) {
	this.back = function($window) {
		return toConsult($window);
	};

	this.save = function(wsdl, $scope) {
		if (wsdl.id == undefined || wsdl.id == null) {
			return CadastroFactory.save(wsdl, $scope);
		} else {
			return CadastroFactory.update(wsdl, $scope);
		}
	};

	this.test = function(DataEditor, $scope, model) {
		if (model.deal == null || model.deal == 'undefined'
				|| model.deal.entryPoints == null
				|| model.deal.entryPoints == 'undefined'
				|| model.deal.entryPoints.length == 0) {
			toTest(DataEditor, TesteFactory, $scope, model);
		} else {
			sendToTest(DataEditor, $scope, model);
		}
	}

	function toConsult($window) {
		$window.location.href = '#!/consult';
	}

	function toTest(DataEditor, TesteFactory, $scope, model) {
		TesteFactory.findDeal(model, $scope).then(function(deal) {
			model.deal = deal;
			sendToTest(DataEditor, $scope, model);
		});
	}

	function sendToTest(DataEditor, $scope, model) {
		DataEditor.Data = model;
		$scope.test(model, 'register');
	}
});