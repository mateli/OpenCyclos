var enableDisableActionButtons = function() {
	var disabled = !$('saveButton').hasClassName('ButtonDisabled');
	var classToAdd = disabled ? 'ButtonDisabled' : 'button'; 
	var classToRemove = disabled ? 'button' : 'ButtonDisabled' ; 		
	
	var buttons = [ $('publishButton'), $('deleteButton')];
	for (var i = 0; i < buttons.length; i++) {
		if (buttons[i] != null) {
			buttons[i].removeClassName(classToRemove);
			buttons[i].addClassName(classToAdd);			
			buttons[i].disabled = disabled;
		}						
	}
}

function isInsert() {
	var id = parseInt(getValue("paymentObligationId"));
	return (isNaN(id) || id == 0);
}

function afterSelectingSeller(seller) {
	setValue("sellerId", seller.id);
}

Behaviour.register({
	'#backButton': function(button) {
		button.onclick = function() {
			var params = $H();
			params.set('paymentObligationId', getValue("paymentObligationId"));
			params.set('guaranteeId', guaranteeId);
			backToLastLocation(params);
		}
	},

	'form': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	},
	
	'#acceptPaymentObligation' : function (button) {
		button.onclick = function() {
			var issuerId = $('issuers').options[$('issuers').selectedIndex].value;
			self.location = pathPrefix + "/acceptPaymentObligation?paymentObligationId=" + button.getAttribute("paymentObligationId") + "&issuerId=" + issuerId;
		}
	},

	'#modifyButton': function(button) {
		button.onclick = function() {
			enableFormFields.apply(button.form);
			getObject('sellerUsername').focus();
		}
	},

	'#sellerUsername': function(input) {
		var div = $('sellersByUsername');
		prepareForMemberAutocomplete(input, div, {paramName:"username", enabled:true, groupIds: sellerGroupIds}, 'sellerId', 'sellerUsername', 'sellerName', 'maxPublishDate', afterSelectingSeller);
		input.onfocus = function() {
			lastMemberFieldWithFocus = this;
		}
	},
	
	'#sellerName': function(input) {
		var div = $('sellersByName');
		prepareForMemberAutocomplete(input, div, {paramName:"name", enabled:true, groupIds: sellerGroupIds}, 'sellerId', 'sellerUsername', 'sellerName', 'maxPublishDate', afterSelectingSeller);
		input.onfocus = function() {
			lastMemberFieldWithFocus = this;
		}
	},
	
	'#publishButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/publishPaymentObligation?paymentObligationId=" + button.getAttribute("paymentObligationId");
		}
	},

	'#concealButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/concealPaymentObligation?paymentObligationId=" + button.getAttribute("paymentObligationId");
		}
	},
	
	'#rejectButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/rejectPaymentObligation?paymentObligationId=" + button.getAttribute("paymentObligationId");
		}
	},

	'#deleteButton': function(button) {
		button.onclick = function() {
			if (confirm(removeConfirmation)) {
				self.location = pathPrefix + "/deletePaymentObligation?paymentObligationId=" + button.getAttribute("paymentObligationId");
			}
		}
	}
});

Event.observe(self, "load", function() {
	if (isInsert()) {
		enableFormForInsert();
	}
	
	if ($('modifyButton') != null) {
		$('modifyButton').observe('click', function(event){
			enableDisableActionButtons();
		});
	}
});