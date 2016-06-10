function isInsert() {
	var poId = $("paymentObligationId");
	return (isNaN(poId) || poId == 0);
}
Behaviour.register({
	'img.paymentObligationDetails': function(img) {
		setPointer(img);
		img.onclick = function() {
			self.location = pathPrefix + "/editPaymentObligation?paymentObligationId=" + img.getAttribute("paymentObligationId");
		}
	},
	
	'#backButton': function(button) {
		button.onclick = function() {
			if (certificationId < 0 && transferId < 0) {
				self.location = pathPrefix + "/searchGuarantees";
			} else if (transferId < 0) {
				var params = $H();
				params.set('guaranteeTypeId', getValue('guarantee(guaranteeType)'));
				params.set('guaranteeId', getValue("guaranteeId"));
				params.set('certificationId', certificationId);
				backToLastLocation(params);
			} else {
				var params = $H();
				params.set('transferId', transferId);
				backToLastLocation(params);				
			}
		}
	},

	'#acceptButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/authorizeGuarantee?guaranteeId=" + button.getAttribute("guaranteeId");
		}
	},

	'#denyButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/denyGuarantee?guaranteeId=" + button.getAttribute("guaranteeId");
		}
	},

	'#cancelButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/cancelGuarantee?guaranteeId=" + button.getAttribute("guaranteeId");
		}
	},

	'#deleteButton': function(button) {
		button.onclick = function() {
			if (confirm(removeConfirmation)) {
				self.location = pathPrefix + "/deleteGuarantee?guaranteeId=" + button.getAttribute("guaranteeId");
			}
		}
	},
	'#loanLink': function(a) {
		setPointer(a);
		a.onclick = function() {
			self.location = pathPrefix + "/loanDetails?loanId=" + a.getAttribute("loanId") + "&guaranteeId=" + a.getAttribute("guaranteeId");			
		}
	}		
});