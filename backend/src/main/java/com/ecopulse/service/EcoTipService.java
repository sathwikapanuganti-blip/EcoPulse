package com.ecopulse.service;

import com.ecopulse.entity.EcoTip;
import com.ecopulse.repository.EcoTipRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EcoTipService {

    private final EcoTipRepository repository;

    public EcoTipService(EcoTipRepository repository) {
        this.repository = repository;
    }

    public List<EcoTip> getTips(String category, String targetGroup, String lang) {
        String langCode = (lang != null && !lang.trim().isEmpty()) ? lang.toLowerCase() : "en";
        
        List<EcoTip> tips = repository.findAll();
        if (tips.isEmpty()) {
            tips = seedInitialTips();
        }

        return tips.stream()
                .filter(t -> category == null || category.equalsIgnoreCase("ALL") || t.getCategory().equalsIgnoreCase(category))
                .filter(t -> targetGroup == null || targetGroup.equalsIgnoreCase("ALL") || t.getTargetGroup().equalsIgnoreCase(targetGroup) || t.getTargetGroup().equalsIgnoreCase("ALL_AGES"))
                .filter(t -> langCode.equals("en") || t.getLanguageCode().equalsIgnoreCase(langCode) || t.getLanguageCode().equalsIgnoreCase("en"))
                .toList();
    }

    private List<EcoTip> seedInitialTips() {
        List<EcoTip> seeds = new ArrayList<>();
        seeds.add(new EcoTip("SAVE_WATER", "Brush Teeth with a Cup", "Letting the tap run while brushing wastes 6 litres of drinking water every single minute.", "Use a small rinse cup instead of keeping the water running.", "KIDS", "en"));
        seeds.add(new EcoTip("SAVE_WATER", "Quick Fix for Dripping Faucets", "A faucet that drips one drop per second wastes over 3,000 gallons of water per year.", "Report leaking taps immediately using the EcoPulse Water Waste tab.", "ELDERS", "en"));
        seeds.add(new EcoTip("REDUCE_FOOD_WASTE", "The First-In, First-Out Fridge Rule", "Food placed at the back of shelves is often forgotten until past its prime.", "Place newer groceries in the back and older items right at eye level.", "FAMILIES", "en"));
        seeds.add(new EcoTip("REDUCE_FOOD_WASTE", "Community Leftover Sharing", "Event buffets frequently produce surplus, high-quality untouched meals.", "Post surplus meals on EcoPulse Food Waste board within 2 hours of meal completion.", "ALL_AGES", "en"));
        seeds.add(new EcoTip("REDUCE_AIR_POLLUTION", "Turn Off Engine at Long Traffic Lights", "Idling your car engine for more than 10 seconds uses more fuel and emits more pollution than restarting it.", "Cut the engine if a red signal is greater than 30 seconds.", "ALL_AGES", "en"));
        seeds.add(new EcoTip("REDUCE_PLASTIC", "Carry a Cotton Shopping Bag", "Plastic grocery bags can take up to 500 years to break down into toxic microplastics in our soil and waterways.", "Keep 2 foldable cloth bags by your house front door.", "ALL_AGES", "en"));
        seeds.add(new EcoTip("ENERGY_SAVING", "Vampire Power Shutdown", "Appliances plugged in on standby account for up to 10% of household electric bills.", "Switch off socket plugs for chargers, TVs, and microwaves when sleeping.", "ELDERS", "en"));
        seeds.add(new EcoTip("SUSTAINABLE_TRANSPORT", "Cycle or Walk for Trips Under 1.5 km", "Short car trips emit triple the pollution per kilometer because cold catalytic converters haven't warmed up.", "Use footwear or bicycles for milk and vegetable runs.", "ALL_AGES", "en"));
        seeds.add(new EcoTip("RECYCLING", "Rinse Containers Before Binning", "Food residue contaminates entire batches of recyclable cardboard and plastics.", "Give cans, jars, and cartons a quick 3-second rinse before tossing them in the recycling bin.", "KIDS", "en"));
        seeds.add(new EcoTip("COMMUNITY_CLEANLINESS", "Never Burn Dry Leaves or Plastic Trash", "Open burning produces dioxins, carbon monoxide, and toxic fine particulate matter (PM2.5).", "Compost organic garden leaves or notify your local society composting team.", "ALL_AGES", "en"));

        return repository.saveAll(seeds);
    }
}
