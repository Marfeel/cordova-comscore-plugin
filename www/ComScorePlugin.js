

var ComScorePlugin = {
	setCustomerData: function(customerID, customerKey, success, error) {
		cordova.exec(success, error, 'ComScorePlugin', 'setCustomerData', [customerID, customerKey]);
	},
	onEnterForeground: function(success, error) {
		cordova.exec(success, error, 'ComScorePlugin', 'onEnterForeground', []);
	},
	onExitForeground: function(success, error) {
		cordova.exec(success, error, 'ComScorePlugin', 'onExitForeground', []);
	},
	start: function (success, error) {
		cordova.exec(success, error, 'ComScorePlugin', 'start', []);
	},
    updateConsent: function(consent, success, error) {
        cordova.exec(success, error, 'ComScorePlugin', 'updateConsent', [consent]);
    }
};

module.exports = ComScorePlugin;
