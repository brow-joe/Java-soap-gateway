app.controller('CadastroController', function($scope, $window, CadastroService,
		DataEditor) {
	updateData($scope, DataEditor);

	$scope.back = function() {
		CadastroService.back($window);
	};

	$scope.save = function() {
		var wsdl = updateParametersJson($scope.model);
		CadastroService.save(wsdl, $scope).then(
				function(response) {
					if (response != null && response != 'undefined') {
						$scope.openModal('success', 'Salvo!', '',
								'Registro atualizado', false, false, '');
					}
					CadastroService.back($window);
				});
	};

	$scope.toTest = function(model) {
		CadastroService.test(DataEditor, $scope, model);
	}

	function updateData($scope, DataEditor) {
		data = DataEditor.Data;
		if (data != null && data != 'undefined') {
			if (data.deal == "") {
				delete data.deal;
			}
		}
		$scope.model = data;
	}

	function updateParametersJson(data) {
		if (data) {
			var deal = data.deal;
			if (deal) {
				var entryPoints = deal.entryPoints;
				entryPoints.forEach(function(entry) {
					try {
						if (entry.parametersArrayJson) {
							var parametersArrayJson =  entry.parametersArrayJson;
							var array = [];
							parametersArrayJson.forEach(function(parameter) {
								var id = parameter.id;
								array.push(id);
							});
							var str = JSON.stringify(array);
							entry.parametersArrayJson = str;
						}
					} catch (e) {
					}
				});
			}
		}

		return data;
	}
});