app.factory('TesteFactory',
		function($http) {
			var factory = {};

			factory.findDeal = function(wsdl, $scope) {
				var request = {
					method : 'POST',
					url : host_app + '/deal/findByWsdl/',
					headers : {
						'Content-Type' : 'application/json'
					},
					data : wsdl
				};

				return $http(request).then(
						function successCallback(response) {
							var deal = {}
							if (response.status == 200) {
								deal = response.data;
							}
							return deal;
						},
						function errorCallback(response) {
							$scope.openModal('danger', 'Erro!', '',
									response.statusText, false, false, '');
						});
			};

			factory.execute = function(entry, $scope) {
				var request = {
					method : 'POST',
					url : host_app + '/entry/execute/?synchronous='
							+ entry.synchronous,
					headers : {
						'Content-Type' : 'application/json'
					},
					data : entry
				};

				return $http(request).then(
						function successCallback(response) {
							var data = {}
							if (response.status == 200) {
								data = response.data;
							}
							return data;
						},
						function errorCallback(response) {
							$scope.openModal('danger', 'Erro!', '',
									response.statusText, false, false, '');
						});
			};

			factory.findAllMessages = function($scope) {
				return $http.get(host_app + '/entry/messages/').then(
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

			factory.removeMessage = function(message, $scope) {
				var request = {
					method : 'DELETE',
					url : host_app + '/entry/messages/',
					headers : {
						'Content-Type' : 'application/json'
					},
					data : message
				};

				return $http(request).then(
						function successCallback(response) {
							var id = message.id;
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