Behaviour.register({
	'#backButton': function(button) {
		button.onclick = function() {
			history.back();
		}
	},
	
	'form': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	},
	
	'img.details': function(img) {
		setPointer(img);
		img.onclick = function() {
			self.location = pathPrefix + "/editBrokerCommissionContract?brokerCommissionContractId=" + img.getAttribute("brokerCommissionContractId");
		}
	},
	
	
	'input.button.suspend': function(button) {
		button.onclick = function() {
			if (confirm(suspendConfirmation)) {
				var brokerId = getValue("brokerId");
				var brokerCommissionId = button.getAttribute("brokerCommissionId");
				self.location = pathPrefix + "/suspendBrokerCommission?brokerCommissionId=" + brokerCommissionId + "&brokerId=" + brokerId;
			}
		}
	},
	
	'input.button.stop': function(button) {
		button.onclick = function() {
			if (confirm(stopConfirmation)) {
				var brokerId = getValue("brokerId");
				var brokerCommissionId = button.getAttribute("brokerCommissionId");
				self.location = pathPrefix + "/stopBrokerCommission?brokerCommissionId=" + brokerCommissionId + "&brokerId=" + brokerId;
			}
		}
	},
	
	'input.button.unsuspend': function(button) {
		button.onclick = function() {
			if (confirm(unsuspendConfirmation)) {
				var brokerId = getValue("brokerId");
				var brokerCommissionId = button.getAttribute("brokerCommissionId");
				self.location = pathPrefix + "/unsuspendBrokerCommission?brokerCommissionId=" + brokerCommissionId + "&brokerId=" + brokerId;
			}
		}
	},
	
	'#memberUsername': function(input) {
		var div = $('membersByUsername');
		prepareForMemberAutocomplete(input, div, {paramName:"username"}, 'memberId', 'memberUsername', 'memberName');
	},
	
	'#memberName': function(input) {
		var div = $('membersByName');
		prepareForMemberAutocomplete(input, div, {paramName:"name"}, 'memberId', 'memberUsername', 'memberName');
	}

});