app.controller('MensagemController', function($scope, $window, TesteService) {
	$scope.back = function() {
		$window.history.back();
	};

	$scope.exibe = function(data) {
		data.json = JSON.stringify(data.json);
		var result = JSON.stringify(JSON.parse(data.json).result);
		TesteService.removeMessage(data, $scope).then(
				function(message) {
					if (message != null && message != 'undefined') {
						$scope.openModal('success', 'Resposta!', '', '', true,
								true, result);
						TesteService.findAllMessages($scope).then(
								function(list) {
									$scope.messages = list;
									loadMessageData($scope);
								});
					} else {
						data.json = JSON.parse(data.json);
					}
				});
	};

	loadMessageData($scope);

	function loadMessageData($scope) {
		var data = [];
		var i;

		$.each($scope.messages, function(index, value) {
			var item = jQuery.extend(true, {}, value);
			item.json = JSON.parse(item.json);
			data.push(item);
		});

		$scope.dataMessages = data;
	}
});