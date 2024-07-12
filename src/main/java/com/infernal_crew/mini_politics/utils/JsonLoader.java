package com.infernal_crew.mini_politics.utils;

import com.infernal_crew.mini_politics.Main;
import com.infernal_crew.mini_politics.budget.BudgetExpense;
import com.infernal_crew.mini_politics.budget.BudgetIncome;
import com.infernal_crew.mini_politics.event.*;
import com.infernal_crew.mini_politics.game.RoundCondition;
import com.infernal_crew.mini_politics.indicators.IndicatorChange;
import com.infernal_crew.mini_politics.indicators.IndicatorCondition;
import com.infernal_crew.mini_politics.indicators.TraitIndicatorEffect;
import com.infernal_crew.mini_politics.jobs.*;
import com.infernal_crew.mini_politics.media.MediaCondition;
import com.infernal_crew.mini_politics.media.MediaIdCondition;
import com.infernal_crew.mini_politics.media.MediaTakeover;
import com.infernal_crew.mini_politics.modifiers.ModifierCondition;
import com.infernal_crew.mini_politics.modifiers.ModifierInvocation;
import com.infernal_crew.mini_politics.modifiers.ModifierRemoval;
import com.infernal_crew.mini_politics.party.IdeologyChange;
import com.infernal_crew.mini_politics.party.IdeologyCondition;
import com.infernal_crew.mini_politics.party.LoyaltyChange;
import com.infernal_crew.mini_politics.party.MPTransfer;
import com.infernal_crew.mini_politics.policy.PolicyChange;
import com.infernal_crew.mini_politics.policy.PolicyCondition;
import com.infernal_crew.mini_politics.story.ShowNote;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JsonLoader {
    public static <class_> ArrayList<class_> loadFiles(Class<class_> class_, String path, Moshi moshi) throws URISyntaxException, IOException {
        ArrayList<class_> list = new ArrayList<>();
        JsonAdapter<class_> jsonAdapter = moshi.adapter(class_);

        URL dir = Main.class.getResource(path);
        List<File> collect = Files.walk(Paths.get(dir.toURI()))
                .filter(Files::isRegularFile)
                .map(Path::toFile).toList();

        for (File f : collect) {
            list.add(jsonAdapter.indent("  ").fromJson(Files.readString(f.toPath())));
        }
        return list;
    }

    public static Moshi gameBuilder() {
        return new Moshi.Builder()
                .add(PolymorphicJsonAdapterFactory.of(Effect.class, "type")
                        .withSubtype(IndicatorChange.class, "indicator_change")
                        .withSubtype(RandomAdvisorEmployment.class, "random_advisor_employment")
                        .withSubtype(AdvisorEmployment.class, "advisor_employment")
                        .withSubtype(RandomAdvisorDismissal.class, "random_advisor_dismissal")
                        .withSubtype(AdvisorDismissal.class, "advisor_dismissal")
                        .withSubtype(ModifierInvocation.class, "modifier_invocation")
                        .withSubtype(ModifierRemoval.class, "modifier_removal")
                        .withSubtype(MediaTakeover.class, "media_takeover")
                        .withSubtype(IdeologyChange.class, "ideology_change")
                        .withSubtype(PolicyChange.class, "policy_change")
                        .withSubtype(BudgetExpense.class, "budget_expense")
                        .withSubtype(BudgetIncome.class, "budget_income")
                        .withSubtype(AdvisorPositionEmployment.class, "advisor_position_employment")
                        .withSubtype(DialogueInvocation.class, "dialogue_invocation")
                        .withSubtype(MPTransfer.class, "mp_transfer")
                        .withSubtype(LoyaltyChange.class, "loyalty_change")
                        .withSubtype(ShowNote.class, "show_note"))
                .add(PolymorphicJsonAdapterFactory.of(Condition.class, "type")
                        .withSubtype(ModifierCondition.class, "modifier_condition")
                        .withSubtype(AdvisorCondition.class, "advisor_condition")
                        .withSubtype(MediaCondition.class, "media_condition")
                        .withSubtype(MediaIdCondition.class, "media_id_condition")
                        .withSubtype(AdvisorSkillCondition.class, "trait_condition")
                        .withSubtype(IndicatorCondition.class, "indicator_condition")
                        .withSubtype(SomeAdvisorCondition.class, "some_advisor_condition")
                        .withSubtype(IdeologyCondition.class, "ideology_condition")
                        .withSubtype(PolicyCondition.class, "policy_condition")
                        .withSubtype(RoundCondition.class, "round_condition")
                        .withSubtype(PersonCondition.class, "person_condition"))
                .add(PolymorphicJsonAdapterFactory.of(TraitEffect.class, "type")
                        .withSubtype(TraitIndicatorEffect.class, "indicator_effect")
                        .withSubtype(LoyaltyChange.class, "loyalty_change"))
                .add(PolymorphicJsonAdapterFactory.of(Part.class, "type")
                        .withSubtype(DescriptionPart.class, "description")
                        .withSubtype(ChoicePart.class, "choice")
                        .withSubtype(PersonPart.class, "person"))
                .add(new ColorAdapter()).build();
    }
}
