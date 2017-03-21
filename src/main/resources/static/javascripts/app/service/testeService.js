app.service('TesteService', function(TesteFactory) {
	this.back = function($window, location) {
		if (location == null || location == 'undefined') {
			location = '#!/consult';
		}
		$window.location.href = location;
		return;
	};

	this.execute = function(entry, $scope) {
		return TesteFactory.execute(entry, $scope);
	};

	this.findAllMessages = function($scope) {
		return TesteFactory.findAllMessages($scope);
	};

	this.removeMessage = function(message, $scope) {
		return TesteFactory.removeMessage(message, $scope);
	};
});