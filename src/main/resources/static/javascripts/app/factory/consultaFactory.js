app.factory('ConsultaFactory', function($http) {
	var factory = {};
	factory.findAll = function($scope) {
		return $http.get(host_app + '/wsdl/').then(
				function successCallback(response) {
					var list = {}
					if (response.status == 200) {
						list = response.data;
					}
					return list;
				},
				function errorCallback(response) {
					$scope.openModal('danger', 'Erro!', '',
							response.statusText, false, false, '');
				});
	};

	factory.remove = function(wsdl, $scope) {
		var request = {
			method : 'DELETE',
			url : host_app + '/wsdl/',
			headers : {
				'Content-Type' : 'application/json'
			},
			data : wsdl
		};

		return $http(request).then(
				function successCallback(response) {
					var id = wsdl.id;
					if (response.status == 200) {
						id = response.data;
					}
					return id;
				},
				function errorCallback(response) {
					$scope.openModal('danger', 'Erro!', '',
							response.statusText, false, false, '');
				});
	};
	return factory;
});