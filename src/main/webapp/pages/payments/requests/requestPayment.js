var currentPage = 0;

Behaviour.register({
	'form': function(form) {
		form.onsubmit = function() {
			return requestValidation(form);
		}
	},
	
	'#memberUsername': function(input) {
		var div = $('membersByUsername');
		prepareForMemberAutocomplete(input, div, {paramName:"username", enabled:true, exclude: toMember}, 'memberId', 'memberUsername', 'memberName', 'amount');
	},
	
	'#memberName': function(input) {
		var div = $('membersByName');
		prepareForMemberAutocomplete(input, div, {paramName:"name", enabled:true, exclude: toMember}, 'memberId', 'memberUsername', 'memberName', 'amount');
	},
	
	'#queryMemberUsername': function(input) {
		var div = $('queryMembersByUsername');
		prepareForMemberAutocomplete(input, div, {paramName:"username", enabled:true, exclude: toMember}, 'queryMemberId', 'queryMemberUsername', 'queryMemberName');
	},
	
	'#queryMemberName': function(input) {
		var div = $('queryMembersByName');
		prepareForMemberAutocomplete(input, div, {paramName:"name", enabled:true, exclude: toMember}, 'queryMemberId', 'queryMemberUsername', 'queryMemberName');
	},
	
	'#searchButton': function(button) {
		button.onclick = updateSearch;
	}
});

function applyPrint(img) {
    setPointer(img)
    img.onclick = function() {
        var transferId = img.getAttribute("transferId");
        if (isReceiptPrinterSet()) {
            printReceipt(context + "/do/transactionReceipt?transferId=" + transferId);
        } else {
            printResults(null, context + "/do/printTransaction?transferId=" + transferId, 500, 300);
        }
    }
}

function updateSearch() {
	$('paginationDisplay').innerHTML = '';
	var params = $H();
	params.set('groupedStatus', getValue("statusSelect"));
	params.set('from', getValue("queryMemberId"));
	params.set('currentPage', currentPage);
	new Ajax.Updater({success: 'resultContainer'}, pathPrefix + "/searchPaymentRequests", {
		method: 'post',
		evalScripts: true,
		postBody: params.toQueryString()
	});
}

Event.observe(self, "load", function() {
	setFocus('memberUsername');
	
	//Update the search now...
	updateSearch();
	
	//... and every 5s
	setInterval(updateSearch, 5000);
});
