Behaviour.register({
	'#backButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/references?nature=TRANSACTION"
		}
	},
	
	'img.edit': function(img) {
		setPointer(img);
		img.onclick = function() {
			var transferId = img.getAttribute("transferId");
			var scheduledPaymentId = img.getAttribute("scheduledPaymentId");
			var paramName = isEmpty(transferId) ? 'scheduledPaymentId' : 'transferId';
			var paramValue = isEmpty(transferId) ? scheduledPaymentId : transferId;
			self.location = pathPrefix + "/transactionFeedbackDetails?" + paramName + "=" + paramValue;
		}
	}
});