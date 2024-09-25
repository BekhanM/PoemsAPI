package app;

import config.HibernateConfig;
import dao.PoemDAO;
import entities.Poem;
import entities.Type;
import io.javalin.Javalin;
import jakarta.persistence.EntityManagerFactory;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("poems");


        PoemDAO pdao = new PoemDAO(emf);

        var app = Javalin.create((config) -> {
            config.router.contextPath = "/poem";
            config.bundledPlugins.enableRouteOverview("/routes");
        });


        //GET ALL POEMS
        app.get("/", ctx -> {
            ctx.json(pdao.getAllPoems());
        });

        //FIND POEM BY ID
        app.get("/{id}", ctx -> {
            Long id = Long.parseLong(ctx.pathParam("id"));
            ctx.json(pdao.findPoemById(id));
        });

        //CREATE A POEM
        app.post("/createpoem", ctx -> {
            String poem = ctx.formParam("poem");
            String typeString = ctx.formParam("type");
            Type type = Type.valueOf(typeString);
            String author = ctx.formParam("author");
            String title = ctx.formParam("title");

            pdao.create(Poem.builder().
                    poem(poem)
                    .type(type)
                    .author(author)
                    .title(title)
                    .build());

            ctx.status(201);
        });

        //UPDATE POEM BY ID
        app.put("/updatepoem/{id}", ctx -> {
            Long id = Long.parseLong(ctx.pathParam("id"));
            Poem p = pdao.findPoemById(id);

            String poem = ctx.formParam("poem");
            String typeString = ctx.formParam("type");
            Type type = Type.valueOf(typeString);
            String author = ctx.formParam("author");
            String title = ctx.formParam("title");

            p.setPoem(poem);
            p.setType(type);
            p.setAuthor(author);
            p.setTitle(title);

            pdao.updatePoem(p);
        });

        //DELETE POEM BY ID
        app.delete("/deletepoem/{id}", ctx -> {
            Long id = Long.parseLong(ctx.pathParam("id"));
            pdao.delete(id);
        });


        app.start(7000);

















        /*
        PoemDAO pdao = new PoemDAO(emf);

        pdao.create(Poem.builder().
                poem("They say I'm possessed, it's an omen\n" +
                        "I keep it 300, like the Romans\n" +
                        "300 bitches, where's the Trojans?")
                .type(Type.Rhymed_verse)
                .author("Ye")
                .title("Black Skinhead")
                .build());

        pdao.create(Poem.builder().
                poem("Now if I fuck this model\n" +
                        "And she just bleached her asshole\n" +
                        "And I get bleach on my T-shirt\n" +
                        "I'ma feel like an asshole")
                .type(Type.Rhymed_verse)
                .author("Ye")
                .title("Father Stretch My Hands Pt. 1")
                .build());

        pdao.create(Poem.builder().
                poem("When I met you I admit my first thoughts was to trick\n" +
                        "You look so good huh, I suck on your daddy's dick\n" +
                        "I never felt that way in my life\n" +
                        "It didn't take long before I made you my wife")
                .type(Type.Rhymed_verse)
                .author("Biggie")
                .title("Me & My Bitch ")
                .build());

        pdao.create(Poem.builder().
                poem("I had a dream I could buy my way to heaven\n" +
                        "When I awoke, I spent that on a necklace\n" +
                        "I told God I'd be back in a second\n" +
                        "Man, it's so hard not to act reckless")
                .type(Type.Rhymed_verse)
                .author("Ye")
                .title("Can't tell me nothing")
                .build());

        pdao.create(Poem.builder().
                poem("I roll with some fiends, I love 'em to death\n" +
                        "I got a few mil' but not all of them rich\n" +
                        "What good is the bread if my niggas is broke?\n" +
                        "What good is first class if my niggas can't sit?")
                .type(Type.Rhymed_verse)
                .author("J. Cole")
                .title("Middle Child")
                .build());

        pdao.create(Poem.builder().
                poem("I roll with some fiends, I love 'em to death\n" +
                        "I got a few mil' but not all of them rich\n" +
                        "What good is the bread if my niggas is broke?\n" +
                        "What good is first class if my niggas can't sit?")
                .type(Type.Rhymed_verse)
                .author("J. Cole")
                .title("Middle Child")
                .build());

         */
    }
}