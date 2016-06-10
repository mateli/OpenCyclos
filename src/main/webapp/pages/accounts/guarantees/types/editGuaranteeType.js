function currencyChanged() {
	var currencyId = getValue("guaranteeType(currency)");
	if (currencyId != null) {
		// Filter by currency
		var params = $H();
		params.set('currencyId', currencyId);
		
		// Update credit fee select
		params.set('context', 'AUTOMATIC');
		params.set('fromNature', 'MEMBER');
		params.set('toNature', 'SYSTEM');
		findTransferTypes(params, function(tts) {
			setOptions('guaranteeType(creditFeeTransferType)', tts, transferTypeSelectOption, false, 'name', 'id');
		});
				
		// Update issue fee select
		params.set('context', 'AUTOMATIC');
		params.set('fromNature', 'MEMBER');
		params.set('toNature', 'MEMBER');
		findTransferTypes(params, function(tts) {
			setOptions('guaranteeType(issueFeeTransferType)', tts, transferTypeSelectOption, false, 'name', 'id');
		});
		
		// Update forward fee select
		findTransferTypes(params, function(tts) {
			setOptions('guaranteeType(forwardTransferType)', tts, transferTypeSelectOption, false, 'name', 'id');
		});
				
		// Update loan fee select
		params.set('context', 'AUTOMATIC_LOAN');
		params.set('fromNature', 'SYSTEM');
		params.set('toNature', 'MEMBER');
		findTransferTypes(params, function(tts) {
			setOptions('guaranteeType(loanTransferType)', tts, transferTypeSelectOption, false, 'name', 'id');
		});
	} else { 
		// Clean all TT selects
		clearOptions('guaranteeType(creditFeeTransferType)');
		clearOptions('guaranteeType(issueFeeTransferType)');
		clearOptions('guaranteeType(forwardTransferType)');
		clearOptions('guaranteeType(loanTransferType)');
	}		
}


function isNonZero(aString) {
	var result = false;
	var currentChar = 'undefined';
	
	for (var i = 0; i < aString.length; i++) {
		currentChar = parseInt(aString.charAt(i));
		if ((currentChar != 0) && (!isNaN(currentChar))) {
			result = true;
		}
	}
	return result;
}

function showHideCreditFeeTransferType() {
	var isZero = !isNonZero(getValue('guaranteeType(creditFee).fee'));
	var isCreditFeeReadonly = $('isCreditFeeReadonly').checked;
	
	var show = !(isCreditFeeReadonly && isZero);
	
	$$('tr.toHideCreditFee').each(function(tr) {
   		tr[show ? 'show' : 'hide']();
   	});
   	
   	if (!show) {
   		setValue('guaranteeType(creditFeeTransferType)', null);
   	}
}

function showHideIssueFeeTransferType() {
	var isZero = !isNonZero(getValue('guaranteeType(issueFee).fee'));
	var isIssueFeeReadonly = $('isIssueFeeReadonly').checked;
	
	var show = !(isIssueFeeReadonly && isZero);
	
	$$('tr.toHideIssueFee').each(function(tr) {
   		tr[show ? 'show' : 'hide']();
   	});
   	
   	if (!show) {
   		setValue('guaranteeType(issueFeeTransferType)', null);
   	}
}

function setSelectedAuthorizer(authorizer) {
	var authorizedBy = $('authorizedBy');
	for (var i = 0; i < authorizedBy.length; i++) {
		if (authorizedBy[i].value == authorizer) {
			authorizedBy.selectedIndex = i;
			authorizedBy.options[i].selected = true;
			authorizedBy.options[i].defaultSelected = true;			
		} 
	}
}

function setAuthorizersByModel() {
	var selectedAuthorizer = getValue('authorizedBy');
    var options = [];
	if (getValue('guaranteeTypesModels') == 'WITH_PAYMENT_OBLIGATION') {
		for (var i = 0; i < paymentObligationAuthorizers.length; i++) {
			var option = new Option(paymentObligationAuthorizersI18N[i], paymentObligationAuthorizers[i]);
			options.push(option); 
		}
	} else {
		for (var i = 0; i < allAuthorizers.length; i++) {
			var option = new Option(allAuthorizersI18N[i], allAuthorizers[i]);
			options.push(option);
		}				
	}
	setOptions('authorizedBy', options);
	setSelectedAuthorizer(selectedAuthorizer);
}

function showHide() {
	var showForwardTransferType = getValue('guaranteeTypesModels') != 'WITH_BUYER_ONLY';
	var showPaymentObligationPeriod = getValue('guaranteeTypesModels') == 'WITH_PAYMENT_OBLIGATION';
	
	showHideForwardTransferType(showForwardTransferType);
	showHidePaymentObligationPeriod(showPaymentObligationPeriod);
	showHideCreditFeeTransferType();
	showHideIssueFeeTransferType(); 
	showHideFeePayers();
	showHidePendingGuaranteeExpiration();
}

function showHideForwardTransferType(show){
	$$('tr.toHideForward').each(function(tr) {
   		tr[show ? 'show' : 'hide']();
   	});
   	
   	if (!show) {
   		setValue('guaranteeType(forwardTransferType)', null);
   	}
}

function showHidePaymentObligationPeriod(show){
	$$('tr.toHide').each(function(tr) {
   		tr[show ? 'show' : 'hide']();
   	});
   	
   	if (!show) {
   		setValue('guaranteeType(paymentObligationPeriod).number', null);
   		setValue('guaranteeType(paymentObligationPeriod).field', null)
	}
}

function showHideFeePayers() {
	var show = getValue('guaranteeTypesModels') == 'WITH_BUYER_AND_SELLER';
	$$('tr.toHidePayer').each(function(tr) {
   		tr[show ? 'show' : 'hide']();
   	});

   	if (!show) {
   		setValue('guaranteeType(creditFeePayer)', null);
		setValue('guaranteeType(issueFeePayer)', null);
	}	
}

function showHidePendingGuaranteeExpiration() {
    var show = getValue("authorizedBy") != 'NONE';
		
	$$('tr.toHidePendingExpiration').each(function(tr) {
   		tr[show ? 'show' : 'hide']();
   	});			

   	if (!show) {
   		setValue('guaranteeType(pendingGuaranteeExpiration).number', null);
   		setValue('guaranteeType(pendingGuaranteeExpiration).field', null)
	}

}

function isInsert() {
	var id = parseInt(getValue("guaranteeTypeId"));
	return (isNaN(id) || id == 0);
}

Behaviour.register({
	
	'#currenciesSelect': function(select) {
		select.onchange = function (select) {
			currencyChanged();
		};
	},
	
	'#memberUsername': function(input) {
		var div = $('membersByUsername');
		prepareForMemberAutocomplete(input, div, {paramName:"username", enabled:true, exclude:getValue("from")}, 'memberId', 'memberUsername', 'memberName', 'amount');
		input.onfocus = function() {
			lastMemberFieldWithFocus = this;
		}
	},

	'form': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	},
	
	'#backButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/listGuaranteeTypes";
		}
	},
	
	'#memberName': function(input) {
		var div = $('membersByName');
		prepareForMemberAutocomplete(input, div, {paramName:"name", enabled:true, exclude:getValue("from")}, 'memberId', 'memberUsername', 'memberName', 'amount');
		input.onfocus = function() {
			lastMemberFieldWithFocus = this;
		}
	},

	'#modifyButton': function(button) {
		button.onclick = function() {
			setAuthorizersByModel();
			showHide();
			setSelectedAuthorizer(originalAuthorizer);
			enableFormFields.apply(button.form, ['currencyText', 'modelText']);
		};
	},
	
	'#isCreditFeeReadonly': function(checkbox) {
		checkbox.onclick = function(checkbox) {
			showHideCreditFeeTransferType();
		};
	},

	'#isIssueFeeReadonly': function(checkbox) {
		checkbox.onclick = function(checkbox) {
			showHideIssueFeeTransferType();
		};
	},
	
	'#guaranteeTypesModels': function(select) {
		select.onchange = function (select) {
			setAuthorizersByModel();
			showHide();
		};
	},	
	'#authorizedBy': function(select) {
		select.onchange = function (select) {
			showHidePendingGuaranteeExpiration();		
		};
	}
});

Event.observe(self, "load", function() {
	if (isInsert()) {
		enableFormForInsert();
	}
	setAuthorizersByModel();
	showHide();
	$('guaranteeType(creditFee).fee').mask.keyUpFunction = function(event, mask) {
		showHideCreditFeeTransferType();
	};
	$('guaranteeType(issueFee).fee').mask.keyUpFunction = function(event, mask) {
		showHideIssueFeeTransferType();
	};
	if (isInsert()) {
		currencyChanged();
	}
});