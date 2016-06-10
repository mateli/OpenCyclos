Behaviour.register({

	'#printButton': function(button) {
		button.onclick = function() {
			var url = context + "/do/printTransaction?transferId=" + transferId;
			var win = printResults(null, url, 500, 300);
		}
	},
	
	'#newPaymentButton': function(button) {
		button.onclick = function() {
			if (selfPayment) {
				self.location = pathPrefix + "/selfPayment?" + params.toQueryString();
			} else {
				self.location = pathPrefix + "/payment?" + params.toQueryString();
			}
		}
	},
	
	'#backToMemberProfileButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/profile?memberId=" + relatedMember;
		}
	}

});
