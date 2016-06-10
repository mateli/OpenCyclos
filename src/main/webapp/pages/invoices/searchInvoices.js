Behaviour.register({
	'img.details': function(img) {
		setPointer(img);
		img.onclick = function() {
			if (isOperator || isMember && !isBroker) {				
				self.location = pathPrefix + "/invoiceDetails?invoiceId=" + img.getAttribute("invoiceId");
			} else {
				self.location = pathPrefix + "/invoiceDetails?memberId=" + memberId + "&invoiceId=" + img.getAttribute("invoiceId");
			}
		}
	},
	
	'#backButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/profile?memberId=" + memberId;
		}
	},
	
	'#modeButton': function(button) {
		button.onclick = function() {
			var advanced = "true" == getValue("advanced");
			setValue("advanced", !advanced);
			button.form.submit();
		}
	},
	
	'input.direction': function(radio) {
		radio.onclick = function() {
			if (radio.value != lastFilter) {
				radio.form.submit();
			}
		}
	},
	
	'#memberUsername': function(input) {
		var div = $('membersByUsername');
		prepareForMemberAutocomplete(input, div, {paramName:"username"}, 'relatedMemberId', 'memberUsername', 'memberName');
	},
	
	'#memberName': function(input) {
		var div = $('membersByName');
		prepareForMemberAutocomplete(input, div, {paramName:"name"}, 'relatedMemberId', 'memberUsername', 'memberName');
	}	
});