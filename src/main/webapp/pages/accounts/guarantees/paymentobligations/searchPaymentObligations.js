var hideSelectAllAndNone = function() {
	var checkboxes = document.getElementsByName("paymentObligationIds");
	//This condition is used when there are no payment obligations because the element names are named as paymentObligationIds
	if (typeof(checkboxes) != undefined && checkboxes.length == 1) {
		if (multipleSelection) {
			$('selectAllButton').style.display = 'none';
			$('selectNoneButton').style.display = 'none';
		}
	}
};

function afterSelectingBuyer(member) {
	setValue("buyerId", member.id);
}

function selectPaymentObligations(b, includeHidden) {
	var elements = document.getElementsByName("paymentObligationIds");
	for (var i = 0, len = elements.length; i < len; i++) {
		if (elements[i].id != "hiddenPaymentObligation" || includeHidden) {
			elements[i].checked = b;
		}
	}
}

function hasPaymentObligations() {
	var elements = document.getElementsByName("paymentObligationIds");
	for (var i = 0, len = elements.length; i < len; i++) {
		if (elements[i].checked) {
			return true;
		}
	}
	return false;
}

function showNextButton(b) {
	var btn = $("next");
	if (b) {
		btn.style.display = "block";
	} else {
		btn.style.display = "none";
	}
}

function afterSelectingSeller(member) {
	setValue("sellerId", member.id);
}

Behaviour.register({
	'#newButton': function(button) {
		button.onclick = function() {
			self.location = pathPrefix + "/editPaymentObligation";
		}
	},

	'#buyerUsername': function(input) {
		var div = $('buyersByUsername');
		prepareForMemberAutocomplete(input, div, {paramName:"username", enabled:true, groupIds: buyerGroupIds}, 'buyerId', 'buyerUsername', 'buyerName', 'amount', afterSelectingBuyer);
		input.onfocus = function() {
			lastMemberFieldWithFocus = this;
		}
	},
	
	'#buyerName': function(input) {
		var div = $('buyersByName');
		prepareForMemberAutocomplete(input, div, {paramName:"name", enabled:true, groupIds: buyerGroupIds}, 'buyerId', 'buyerUsername', 'buyerName', 'amount', afterSelectingBuyer);
		input.onfocus = function() {
			lastMemberFieldWithFocus = this;
		}
	},
	
	'#sellerUsername': function(input) {
		var div = $('sellersByUsername');
		prepareForMemberAutocomplete(input, div, {paramName:"username", exclude: 0, groupIds: sellerGroupIds}, 'sellerId', 'sellerUsername', 'sellerName', 'amount', afterSelectingSeller);
		input.onfocus = function() {
			lastMemberFieldWithFocus = this;
		}
	},
	
	'#sellerName': function(input) {
		var div = $('sellersByName');
		prepareForMemberAutocomplete(input, div, {paramName:"name", enabled:true, exclude:getValue("from"), groupIds: sellerGroupIds}, 'sellerId', 'sellerUsername', 'sellerName', 'amount', afterSelectingSeller);
		input.onfocus = function() {
			lastMemberFieldWithFocus = this;
		}
	},
		
	'img.details': function(img) {
		setPointer(img);
		img.onclick = function() {
			self.location = pathPrefix + "/editPaymentObligation?paymentObligationId=" + img.getAttribute("paymentObligationIds");
		}
	},
		
	'img.acceptPaymentObligation': function(img) {
		setPointer(img);
		img.onclick = function() {	
			selectPaymentObligations(false, false); //deselect all the checkboxes
			
			//select only the hidden
			var hiddenChk = $('hiddenPaymentObligation');
			hiddenChk.value = img.getAttribute("paymentObligationIds");
			hiddenChk.checked = true;
			
			$('hiddenPaymentObligation').form.submit();
		}
	},

	'#selectAllButton': function(button) {
		button.onclick = function() { 
			selectPaymentObligations(true, false);
			showNextButton(true);
		}
	},
	
	'#selectNoneButton': function(button) {
		button.onclick = function() {
			selectPaymentObligations(false, true);
			showNextButton(false);
		}
	},
	
	'input.checkbox': function(input) {
		input.onclick = function() {
			showNextButton(hasPaymentObligations());
		}
	}
});
Event.observe(self, "load", function() {
	hideSelectAllAndNone();
});