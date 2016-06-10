function prepareFormForSubmit(form, confirmationMessage) {
	if (form.comments) form.comments.value = $("comments").value;
	if (form.showToMember) form.showToMember.value = $("showToMember") ? $("showToMember").checked : true;
	form.transactionPassword.value = getValue("_transactionPassword");
	if (requestValidation(form)) {
		return confirm(confirmationMessage);
	}
	return false;
}
   
Behaviour.register({
	
	'#authorizeForm': function(form) {
		form.onsubmit = function() {
			return prepareFormForSubmit(form, authorizeConfirmationMessage);
		}
	},
	
	'#denyForm': function(form) {
		form.onsubmit = function() {
			if (isEmpty($("comments").value)) {
				alert(commentsRequiredMessage);
				setFocus("comments");
				return false;
			}
			return prepareFormForSubmit(form, denyConfirmationMessage);
		}
	},
	
	'#cancelForm': function(form) {
		form.onsubmit = function() {
			return prepareFormForSubmit(form, cancelConfirmationMessage);
		}
	},
	
	'#chargebackForm': function(form) {
		form.onsubmit = function() {
			return prepareFormForSubmit(form, chargebackConfirmation);
		}
	},
		
	'#backButton': function(button) {
		button.onclick = function() {
			if (isEmpty(memberId) || isEmpty(typeId)) {
				history.back();
			} else {
				params = $H();
				params.set('memberId', memberId);
				params.set('typeId', typeId);
				backToLastLocation(params)
			} 
		}
	},
	
	'.showTransferDetails': function(img) {
		setPointer(img);
		img.onclick = function() {
			self.location = pathPrefix + "/viewTransaction?transferId=" + img.getAttribute("transferId") + "&memberId=" + memberId;
		}
	},
	
	'img.showChild': function(img) {
		setPointer(img);
		img.onclick = function() {
			self.location = pathPrefix + "/viewTransaction?transferId=" + img.getAttribute("childId") + "&memberId=" + memberId;
		}
	},
	
	'img.print': function(img) {
		setPointer(img);
		img.onclick = function() {
		    var transferId = img.getAttribute("transferId");
		    if (isReceiptPrinterSet()) {
		        printReceipt(context + "/do/transactionReceipt?transferId=" + transferId);
		    } else {
		        printResults(null, context + "/do/printTransaction?transferId=" + transferId, 500, 300);
		    }
		}
	},
	
	'#payNowButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/confirmScheduledPayment?transferId=" + button.getAttribute("transferId");
		}
	},
	
	'#toggleActionsLink': function(a) {
		setPointer(a);
		a.onclick = function() {
			$$('tr.trAction').each(function(tr) {
				tr.show();
			})
			$('trActions').hide();
		}
	},

	'.showGuaranteeDetails': function(img) {
		setPointer(img);
		img.onclick = function() {
			if (img.getAttribute("transferId") > 0) {
				self.location = pathPrefix + "/guaranteeDetails?guaranteeId=" + img.getAttribute("guaranteeId") + "&transferId=" + img.getAttribute("transferId");
			} else { 
				self.location = pathPrefix + "/guaranteeDetails?guaranteeId=" + img.getAttribute("guaranteeId");
			}
		}
	}	
	
});