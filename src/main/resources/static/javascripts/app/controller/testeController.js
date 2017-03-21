app.controller('TesteController', function($scope, $window, TesteService) {
	$scope.model = updateParametersJson($scope.executor);

	$scope.back = function() {
		TesteService.back($window, $scope.location);
	};

	$scope.execute = function(entry) {
		var data = jQuery.extend(true, {}, entry);
		data.wsdl = $scope.model.url;
		data.url = $scope.model.deal.url;
		data.wsdlId = $scope.id;
		TesteService.execute(data, $scope).then(
				function(response) {
					if (response != null && response != 'undefined') {
						if (data.synchronous == true) {
							$scope.openModal('success', 'Resposta!', '', '',
									true, true, response.result);
						} else {
							$scope.openModal('success', 'Resposta!', '',
									'Requisicao enviada', false, false, '');
						}
					}
				});
	};

	function updateParametersJson(data) {
		if (data) {
			var deal = data.deal;
			if (deal) {
				var entryPoints = deal.entryPoints;
				entryPoints.forEach(function(entry) {
					try {
						var json = JSON.parse(entry.parametersArrayJson);
						var array = [];
						json.forEach(function(item) {
							var obj = {};
							obj.id = item;
							obj.value = '';
							array.push(obj);
						});
						entry.parametersArrayJson = array;
					} catch (e) {
					}
				});
			}
		}

		return data;
	}
});