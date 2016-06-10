function calculateFee() {
	var params = $H();
	params.set('guaranteeTypeId', getValue('guaranteeTypeId'));
	params.set('amount', $('amount').getAttribute('amount'));
	params.set('creditFeeSpec.type', getValue('guarantee(creditFeeSpec).type'));
	params.set('creditFeeSpec.fee', getValue('guarantee(creditFeeSpec).fee'));
	params.set('issueFeeSpec.type', getValue('guarantee(issueFeeSpec).type'));
	params.set('issueFeeSpec.fee', getValue('guarantee(issueFeeSpec).fee'));
	params.set('validity.begin', getValue('guarantee(validity).begin'));
	params.set('validity.end', getValue('guarantee(validity).end'));
	requestValidation(params, pathPrefix + "/calculateGuaranteeFee", currentFeeValue);
}

function showHide(selector, show) {
	$$('tr.' + selector).each(function(tr) {
   		tr[show ? 'show' : 'hide']();
   	});
}

function currentFeeValue(params) {
	if (params.returnValue) {
	
		var creditData = params.xml.getElementsByTagName("currentCreditFeeValue").item(0).firstChild.data;
		var issueData = params.xml.getElementsByTagName("currentIssueFeeValue").item(0).firstChild.data;

		if (creditData != "null") {
			$('creditFeeValueTd').innerHTML = creditData;
			showHide('toHide', true);	
		} else {
			$('creditFeeValueTd').innerHTML = '';
		} 
		
		if (issueData != "null") {
			$('issueFeeValueTd').innerHTML = issueData;
			showHide('toHide', true);
		} else {
			$('issueFeeValueTd').innerHTML = '';
		}
	}
	
	if (!params.returnValue || (issueData == "null" && creditData == "null")) {
		$('creditFeeValueTd').innerHTML = '';
		$('issueFeeValueTd').innerHTML = '';
		showHide('toHide', false);
	}	
}

Behaviour.register({
	'#backButton': function(button) {
		button.onclick = function() {
			var params = $H();
			params.set('guaranteeId', getValue("guaranteeId"));
			backToLastLocation(params);
		}
	},

	'form': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	},

	'#validityBegin':function(input) {
		input.onblur = function(input) {
			calculateFee();
		}
	},
	
	'#validityEnd':function(input) {
		input.onblur = function(input) {
			calculateFee();
		}
	},
	
	'#creditFeeSpecFee':function(input) {
		input.onblur = function(input) {
			calculateFee();
		}
	},
	
	'#creditFeeSpecType':function(input) {
		input.onchange = function(input) {
			calculateFee();
		}
	},
	
	'#issueFeeSpecFee':function(input) {
		input.onblur = function(input) {
			calculateFee();
		}
	},
	
	'#issueFeeSpecType':function(input) {
		input.onchange = function(input) {
			calculateFee();
		}
	}
});

Event.observe(self, "load", function() {
	calculateFee();
});