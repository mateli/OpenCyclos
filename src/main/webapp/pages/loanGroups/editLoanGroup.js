Behaviour.register({
	'form': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	},

	'#modifyButton': function(button) {
		button.onclick = function() {
			enableFormFields.apply(button.form);
			getObject('loanGroup(name)').focus();
		}
	},
	
	'#backButton': function(button) {
		button.onclick = function() {
			var memberId = parseInt(getValue("memberId"));
			if (isNaN(memberId) || memberId <= 0) {
				self.location = pathPrefix + "/searchLoanGroups";
			} else {
				self.location = pathPrefix + "/memberLoanGroups?memberId=" + memberId;
			}
		}
	},
	
	'#viewLoansButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/searchLoans?loanGroupId=" + getValue("loanGroup(id)");
		}
	},
	
	'#grantLoanButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/grantLoan?loanGroupId=" + getValue("loanGroup(id)");
		}
	},
	
	'#memberUsername': function(input) {
		var div = $('membersByUsername');
		prepareForMemberAutocomplete(input, div, {paramName:"username", enabled:true}, 'foundMemberId', 'memberUsername', 'memberName');
	},
	
	'#memberName': function(input) {
		var div = $('membersByName');
		prepareForMemberAutocomplete(input, div, {paramName:"name", enabled:true}, 'foundMemberId', 'memberUsername', 'memberName');
	},
	
	'#addMemberButton': function(button) {
		button.onclick = function() {
			var memberId = getValue("foundMemberId");
			if (isEmpty(memberId)) {
				alert(memberRequiredError);
				setFocus(memberUsername);
				return;
			}
			self.location = pathPrefix + "/addMemberToLoanGroup?loanGroupId=" + getValue("loanGroup(id)") + "&memberId=" + memberId;
		}
	},
	
	'#descriptionText': function(textarea) {
		new SizeLimit(textarea, 1000);
	},
	
	'img.removeMember': function(img) {
		setPointer(img);
		img.onclick = function() {
			if (confirm(removeMemberConfirmationMessage)) {
				self.location = pathPrefix + "/removeMemberFromLoanGroup?loanGroupId=" + getValue("loanGroup(id)") + "&memberId=" + img.getAttribute("memberId");
			}
		}
	}
});

Event.observe(self, "load", function() {
	var name = getObject("loanGroup(name)");
	if (!name.readonly) {
		name.focus();
	}
})