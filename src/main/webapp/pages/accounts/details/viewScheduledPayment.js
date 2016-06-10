function prepareFormForSubmit(form, confirmationMessage) {
	form.transactionPassword.value = getValue('_transactionPassword');
	if (requestValidation(form)) {
		return confirm(confirmationMessage);
	}
	return false;
}

Behaviour.register({
	
	'#blockForm': function(form) {
		form.onsubmit = function() {
			return prepareFormForSubmit(form, blockConfirmationMessage);
		}
	},
	
	'#unblockForm': function(form) {
		form.onsubmit = function() {
			return prepareFormForSubmit(form, unblockConfirmationMessage);
		}
	},
	
	'#cancelForm': function(form) {
		form.onsubmit = function() {
			return prepareFormForSubmit(form, cancelConfirmationMessage);
		}
	},
	
	'#backButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/searchScheduledPayments";
		}
	},
	
	'#payNowButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/confirmScheduledPayment?fromScheduled=true&transferId=" + button.getAttribute("transferId");
		}
	},
	
	'img.showTransfer': function(img) {
		setPointer(img);
		img.onclick = function() {
			self.location = pathPrefix + "/viewTransaction?transferId=" + img.getAttribute("transferId") + "&paymentId=" + paymentId;
		}
	},
	
	'img.print': function(img) {
		setPointer(img);
		img.onclick = function() {
		    var paymentId = img.getAttribute("paymentId");
            if (isReceiptPrinterSet()) {
                printReceipt(context + "/do/scheduledPaymentReceipt?paymentId=" + paymentId);
            } else {
                printResults(null, context + "/do/printScheduledPayment?paymentId=" + paymentId, 500, 400);
            }
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
	}
	
});