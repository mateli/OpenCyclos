Behaviour.register({
	
	'#backButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/profile?memberId=" + getValue("memberId");
		}
	},
	
	'#newContract': function(select) {
		select.onchange = function() {
			var brokerCommissionId = this.value;
			var memberId = getValue("memberId");
			if (!isEmpty(brokerCommissionId)) {
				self.location = pathPrefix + "/editBrokerCommissionContract?brokerCommissionId=" + brokerCommissionId + "&memberId=" + memberId;
			}
		}
	},
	
	'img.details': function(img) {
		setPointer(img);
		img.onclick = function() {
			self.location = pathPrefix + "/editBrokerCommissionContract?brokerCommissionContractId=" + img.getAttribute("brokerCommissionContractId");
		}
	},
	
	'img.remove': function(img) {
		setPointer(img);
		img.onclick = function() {
			if (confirm(removeConfirmation)) {
				var memberId = getValue("memberId");
				self.location = pathPrefix + "/removeBrokerCommissionContract?brokerCommissionContractId=" + img.getAttribute("brokerCommissionContractId") + "&memberId=" + memberId;
			}
		}
	}
});