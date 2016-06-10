Behaviour.register({

	'#backButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/profile?memberId=" + getValue("memberId");
		}
	},
	
	'#setReferenceButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/generalReferenceDetails?memberId=" + getValue("memberId");
		}
	},
	
	'#paymentsAwaitingFeedbackButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/searchPaymentsAwaitingFeedback";
		}
	},

	'input.update': function(radio) {
		radio.onclick = function() {
			radio.form.submit();
		}
	},
	
	'img.details': function(img) {
		setPointer(img);
		img.onclick = function() {
			self.location = pathPrefix + (isGeneral ? "/generalReferenceDetails" : "/transactionFeedbackDetails") + "?referenceId=" + img.getAttribute("referenceId") + "&memberId=" + getValue("memberId");
		};
	},
	
	'img.remove': function(img) {
		setPointer(img);
		img.onclick = function() {
			if (confirm(removeConfirmation)) {
				self.location = pathPrefix + "/removeReference?referenceId=" + img.getAttribute("referenceId") + "&memberId=" + getValue("memberId");
			}
		};
	}
});
