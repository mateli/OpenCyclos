Behaviour.register({
	'#backButton': function(button) {
		button.onclick = function() {
			if (isNaN(memberId) || memberId <= 0) {
				self.location = pathPrefix + "/searchLoanGroups";
			} else {
				self.location = pathPrefix + "/memberLoanGroups?memberId=" + memberId;
			}
		}
	}
});