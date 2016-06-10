Behaviour.register({
	'#memberUsername': function(input) {
		var div = $('membersByUsername');
		prepareForMemberAutocomplete(input, div, {paramName:"username",brokered:true}, 'memberId', 'memberUsername', 'memberName');
	},
	
	'#memberName': function(input) {
		var div = $('membersByName');
		prepareForMemberAutocomplete(input, div, {paramName:"name",brokered:true}, 'memberId', 'memberUsername', 'memberName');
	},
	'img.exportCSV': function(img) {
		setPointer(img);
		img.onclick = function() {
			var form = document.forms[0];
			submitTo(form, pathPrefix + "/exportCardsToCsv");
		}
	},
	
	'img.details': function(img) {
		setPointer(img);
		img.onclick = function() {
				self.location = pathPrefix + "/cardDetails?cardId=" + img.getAttribute("cardId")+ "&listOnly=" + listOnly;
		}
	},
	
	'img.print': function(img) {
		setPointer(img);
		img.onclick = function() {
			var form = document.forms[0];
			printResults(form, pathPrefix + "/printCards");
		}
	},
	
	'#backButton':function(button){
		button.onclick = function(){
			if(listOnly){
				self.location = pathPrefix + "/profile?memberId=" + cardOwner;
			}
		}
	}
});