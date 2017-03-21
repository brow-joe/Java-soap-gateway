app.factory('DataEditor', function() {
	return {};
});

app.factory('CadastroFactory', function($http) {
	var factory = {};
	factory.save = function(wsdl, $scope) {
		var request = {
			method : 'POST',
			url : host_app + '/wsdl/',
			headers : {
				'Content-Type' : 'application/json'
			},
			data : wsdl
		};

		return $http(request).then(
				function successCallback(response) {
					var dto = wsdl;
					if (response.status == 200) {
						dto = response.data;
					}
					return dto;
				},
				function errorCallback(response) {
					$scope.openModal('danger', 'Erro!', '',
							response.statusText, false, false, '');
				});
	};

	factory.update = function(wsdl, $scope) {
		var request = {
			method : 'PUT',
			url : host_app + '/wsdl/',
			headers : {
				'Content-Type' : 'application/json'
			},
			data : wsdl
		};

		return $http(request).then(
				function successCallback(response) {
					var dto = wsdl;
					if (response.status == 200) {
						dto = response.data;
					}
					return dto;
				},
				function errorCallback(response) {
					$scope.openModal('danger', 'Erro!', '',
							response.statusText, false, false, '');
				});
	};

	return factory;
});