Behaviour.register({
	'img.remove': function(img) {
		setPointer(img);
		img.onclick = function() {
			if (confirm(removeConfirmationMessage)) {
				self.location = pathPrefix + "/removeMemberFromLoanGroup?memberId=" + memberId + "&loanGroupId=" + img.getAttribute("loanGroupId");
			}
		}
	},
	
	'img.view': function(img) {
		setPointer(img);
		img.onclick = function() {
			self.location = pathPrefix + "/viewLoanGroup?memberId=" + memberId + "&loanGroupId=" + img.getAttribute("loanGroupId");
		}
	},
	
	'#addForm': function(form) {
		form.onsubmit = function() {
			return confirm(addConfirmationMessage);
		}
	},
	
	'#backButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/profile?memberId=" + memberId;
		}
	}
});