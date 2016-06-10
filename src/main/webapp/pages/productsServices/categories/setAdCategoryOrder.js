Behaviour.register({
	'#backButton': function(button) {
		button.onclick = function() {
			if (currentCategory >0) {
				self.location = pathPrefix + "/editAdCategory?id=" + currentCategory; 
			} else {
				self.location = pathPrefix + "/listAdCategories";
			}
		}
	},
	'#applyOrder': function(a) {
		setPointer(a);		
		a.onclick = function() {
		    if(currentCategory == null || currentCategory == ""){
		    	self.location = pathPrefix + "/setAdCategoryOrder?orderAlpha=true";
		    }else{
				self.location = pathPrefix + "/setAdCategoryOrder?orderAlpha=true&currentCategory=" + currentCategory;
			}
		}
	},
	'.draggableList': function(list) {
		Sortable.create(list);
	}
});