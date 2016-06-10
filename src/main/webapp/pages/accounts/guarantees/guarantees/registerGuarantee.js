function calculateFee() {
	var params = $H();
	params.set('guaranteeTypeId', getValue('guaranteeTypeId'));
	params.set('amount', getValue('guarantee(amount)'));
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
	'form': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	},
	
	'#backButton': function(button) {
		button.onclick = function() {
			var params = $H();
			params.set('guaranteeTypeId', getValue('guarantee(guaranteeType)'));
			backToLastLocation(params);
		}
	},

	'#issuerUsername': function(input) {
		var div = $('issuersByUsername');
		div.style.width = Element.getDimensions(input).width + "px";
		prepareForMemberAutocomplete(input, div, {paramName:"username", groupIds:issuerGroupsId, enabled:true}, 'issuerId', 'issuerUsername', 'issuerName', 'buyerUsername');
	},
	
	'#issuerName': function(input) {
		var div = $('issuersByName');
		div.style.width = Element.getDimensions(input).width + "px";
		prepareForMemberAutocomplete(input, div, {paramName:"name", groupIds:issuerGroupsId, enabled:true}, 'issuerId', 'issuerUsername', 'issuerName', 'buyerUsername');
	},	

	'#sellerUsername': function(input) {
		var div = $('sellersByUsername');
		div.style.width = Element.getDimensions(input).width + "px";
		prepareForMemberAutocomplete(input, div, {paramName:"username", groupIds:sellerGroupsId, enabled:true}, 'sellerId', 'sellerUsername', 'sellerName', 'amount');		
	},
	
	'#sellerName': function(input) {
		var div = $('sellersByName');
		div.style.width = Element.getDimensions(input).width + "px";
		prepareForMemberAutocomplete(input, div, {paramName:"name", groupIds:sellerGroupsId, enabled:true}, 'sellerId', 'sellerUsername', 'sellerName', 'amount');
	},	
	
	'#buyerUsername': function(input) {
		var div = $('buyersByUsername');
		div.style.width = Element.getDimensions(input).width + "px";
		var map = {paramName:"username", enabled:true};
		if (buyerGroupsId) {
			map = {paramName:"username", groupIds:buyerGroupsId, enabled:true};
		}
		
		prepareForMemberAutocomplete(input, div, map, 'buyerId', 'buyerUsername', 'buyerName', isWithBuyerAndSeller ? 'sellerUsername' : 'amount');
	},
	
	'#buyerName': function(input) {
		var div = $('buyersByName');
		div.style.width = Element.getDimensions(input).width + "px";
		var map = map = {paramName:"name", enabled:true};
		if (buyerGroupsId) {
			map = {paramName:"name", groupIds:buyerGroupsId, enabled:true};;
		}
		
		prepareForMemberAutocomplete(input, div, map, 'buyerId', 'buyerUsername', 'buyerName', isWithBuyerAndSeller ? 'sellerUsername' : 'amount');
	},
	
	'#amount':function(input) {
		input.onblur = function(input) {
			calculateFee();
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
	getObject('issuerUsername').focus();
});