var isWithBuyerOnly = function() {
		var guaranteeType = document.getElementById('guaranteeType');
		if (guaranteeType != null) {		
			var guaranteeTypeId = guaranteeType.value

			for (var i = 0, len = guaranteeTypeIdsWithBuyerOnly.length; i < len; i++) {
				if (guaranteeTypeIdsWithBuyerOnly[i] == guaranteeTypeId) {
					return true;
				}
			}
		} 
		return false;
    }

var isMyGuaranteesOnly = function() {
		var withBuyerOnly = document.getElementById('withBuyerOnly');
		
		return withBuyerOnly != null && withBuyerOnly.checked;
    }

var isAllGuaranteeTypes = function() {
		return getValue("guaranteeType") == "";
    }

var hideSeller = function() {
		var hide = isWithBuyerOnly() || isMyGuaranteesOnly() || isAllGuaranteeTypes(); 
		if (hide) {
			if (document.getElementById("sellerUsername") != null) {		
				setValue("sellerUsername", null);
				setValue("sellerName", null);
				setValue("sellerId", null);
			}
		}
		$$('tr.toHideSeller').each(function(tr) {
	   		tr[hide ? 'hide' : 'show']();
	   	});
	   	
	   	return hide;
	}

var hideMember = function() {
		var hide = !isAllGuaranteeTypes(); 
		if (hide) {
			if (document.getElementById("memberUsername") != null) {		
				setValue("memberUsername", null);
				setValue("memberName", null);
				setValue("memberId", null);
			}
		}
		$$('tr.toHideMember').each(function(tr) {
	   		tr[hide ? 'hide' : 'show']();
	   	});
	   	
	   	return hide;
	}

var hideBuyer = function(hide) {
		var hide = isMyGuaranteesOnly() || isAllGuaranteeTypes();
		if (hide) {
			if (document.getElementById("buyerUsername") != null) {
				setValue("buyerUsername", null);
				setValue("buyerName", null);
				setValue("buyerId", null);
			}
		}
		$$('tr.toHideBuyer').each(function(tr) {
	   		tr[hide ? 'hide' : 'show']();
	   	});
	   	
	   	return hide; 
	}

Behaviour.register({
	'img.guaranteeDetails': function(img) {
		setPointer(img);
		img.onclick = function() {
			self.location = pathPrefix + "/guaranteeDetails?guaranteeId=" + img.getAttribute("guaranteeId");
		}
	},

	'#issuerUsername': function(input) {
		var div = $('issuersByUsername');
		div.style.width = Element.getDimensions(input).width + "px";
		prepareForMemberAutocomplete(input, div, {paramName:"username", groupIds:issuerGroupsId}, 'issuerId', 'issuerUsername', 'issuerName');
	},
	
	'#issuerName': function(input) {
		var div = $('issuersByName');
		div.style.width = Element.getDimensions(input).width + "px";
		prepareForMemberAutocomplete(input, div, {paramName:"name", groupIds:issuerGroupsId}, 'issuerId', 'issuerUsername', 'issuerName');
	},	
	
	'#buyerUsername': function(input) {
		buyerUsernameMap = {paramName:"username", groupIds:buyerGroupsId};
		var buyerOnly =  isWithBuyerOnly();
		if (buyerOnly) {
			buyerUsernameMap = {paramName:"username"};
		}
		
		var div = $('buyersByUsername');
		div.style.width = Element.getDimensions(input).width + "px";
		prepareForMemberAutocomplete(input, div, buyerUsernameMap, 'buyerId', 'buyerUsername', 'buyerName');
	},
	
	'#buyerName': function(input) {	
		buyerNameMap = {paramName:"name", groupIds:buyerGroupsId};
		var buyerOnly =  isWithBuyerOnly();
		if (buyerOnly) {
			buyerNameMap = {paramName:"name"};
		}
		
		var div = $('buyersByName');
		div.style.width = Element.getDimensions(input).width + "px";
		prepareForMemberAutocomplete(input, div, buyerNameMap, 'buyerId', 'buyerUsername', 'buyerName');
	},	

	'#sellerUsername': function(input) {
		var div = $('sellersByUsername');
		div.style.width = Element.getDimensions(input).width + "px";
		prepareForMemberAutocomplete(input, div, {paramName:"username", groupIds:sellerGroupsId}, 'sellerId', 'sellerUsername', 'sellerName');
	},
	
	'#sellerName': function(input) {
		var div = $('sellersByName');
		div.style.width = Element.getDimensions(input).width + "px";
		prepareForMemberAutocomplete(input, div, {paramName:"name", groupIds:sellerGroupsId}, 'sellerId', 'sellerUsername', 'sellerName');
	},

	'#memberUsername': function(input) {
		var div = $('membersByUsername');
		div.style.width = Element.getDimensions(input).width + "px";
		prepareForMemberAutocomplete(input, div, {paramName:"username"}, 'memberId', 'memberUsername', 'memberName');
	},
	
	'#memberName': function(input) {
		var div = $('membersByName');
		div.style.width = Element.getDimensions(input).width + "px";
		prepareForMemberAutocomplete(input, div, {paramName:"name"}, 'memberId', 'memberUsername', 'memberName');
	},	
	
	'#newGuarantee': function(select) {
		select.onchange = function() {
			var guaranteeTypeId = this.value;
			if (!isEmpty(guaranteeTypeId)) {
				self.location = pathPrefix + "/registerGuarantee?guaranteeTypeId=" + guaranteeTypeId;
			}
		}
	},

	'#guaranteeType': function(select) {
		select.onchange = function() {
			select.form.submit();
		}
	},

	'#withBuyerOnly': function(buyerOnlyChk) {
		buyerOnlyChk.onclick = function() {	
			hideSeller();
			hideBuyer();
		}
	},

	'img.remove': function(img) {
		setPointer(img);
		img.onclick = function() {
			if (confirm(removeConfirmation)) {
			//TODO: watch the query string
				self.location = pathPrefix + "/removeGuarantee?guaranteeId=" + img.getAttribute("guaranteeId");
			}
		}
	},
	
	'img.exportCSV': function(img) {
		setPointer(img);
		img.onclick = function() {
			var form = document.forms[0];
			//TODO: watch the query string
			submitTo(form, context + "/do/exportGuaranteesToCsv");
		}
	},

	'img.print': function(img) {
		setPointer(img);
		img.onclick = function() {
			var form = document.forms[0];
			//TODO: watch the query string
			printResults(form, context + "/do/printGuarantee");
		}
	}	
});

Event.observe(self, "load", function() {
	hideSeller();
	hideBuyer();
	hideMember();
});